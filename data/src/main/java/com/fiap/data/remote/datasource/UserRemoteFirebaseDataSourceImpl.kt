package com.fiap.data.remote.datasource

import com.fiap.data.remote.mapper.NewUserFirebasePayloadMapper
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.hitg.domain.entity.NewUser
import com.hitg.domain.entity.RequestState
import com.hitg.domain.entity.User
import com.hitg.domain.entity.UserLogin
import kotlinx.coroutines.tasks.await

class UserRemoteFirebaseDataSourceImpl(
    private val mAuth: FirebaseAuth,
    private val firebaseFirestore: FirebaseFirestore
) : UserRemoteDataSource {

    override suspend fun getUserLogged(): RequestState<User> {
        mAuth.currentUser?.reload()

        val firebaseUser = mAuth.currentUser

        return if (firebaseUser == null) {
            RequestState.Error(Exception("Usuário não logado"))
        } else {
            val user = firebaseFirestore.collection("users")
                .document(firebaseUser.uid).get().await().toObject(User::class.java)

            if (user == null) {
                RequestState.Error(java.lang.Exception("Usuário não encontrado"))
            } else {
                user.id = firebaseUser.uid
                RequestState.Success(user)
            }
        }

    }

    override suspend fun doLogin(userLogin: UserLogin): RequestState<User> {

        return try {
            mAuth.signInWithEmailAndPassword(userLogin.email, userLogin.password).await()

            val firebaseUser = mAuth.currentUser

            if (firebaseUser == null) {
                RequestState.Error(Exception("Usuário ou senha inválido"))
            } else {
                RequestState.Success(User(firebaseUser.displayName ?: ""))
            }

        } catch (e: Exception) {
            RequestState.Error(Exception(e))
        }
    }

    override suspend fun create(newUser: NewUser): RequestState<User> {
        return try {
            mAuth.createUserWithEmailAndPassword(newUser.email, newUser.password).await()

            val userId = mAuth.currentUser?.uid
            if (userId == null) {

                RequestState.Error(java.lang.Exception("Não foi possível criar a conta"))

            } else {

                val newUserFirebasePayload =
                    NewUserFirebasePayloadMapper.mapToNewUserFirebasePayload(newUser)

                firebaseFirestore
                    .collection("users")
                    .document(userId)
                    .set(newUserFirebasePayload)
                    .await()

                RequestState.Success(NewUserFirebasePayloadMapper.mapToUser(newUserFirebasePayload))
            }
        } catch (e: java.lang.Exception) {
            RequestState.Error(e)
        }
    }
}