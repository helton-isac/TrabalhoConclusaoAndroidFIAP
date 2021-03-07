package com.fiap.data.di

import com.fiap.data.remote.datasource.UserRemoteDataSource
import com.fiap.data.remote.datasource.UserRemoteFirebaseDataSourceImpl
import com.fiap.data.repository.UserRepositoryImpl
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.hitg.domain.repository.UserRepository
import org.koin.dsl.module

val repositoryModule = module {
    factory<UserRemoteDataSource> {
        UserRemoteFirebaseDataSourceImpl(
            Firebase.auth,
            Firebase.firestore
        )
    }
    factory<UserRepository> {
        UserRepositoryImpl(
            userRemoteDataSource = get()
        )
    }
}

val dataModules = listOf(repositoryModule)