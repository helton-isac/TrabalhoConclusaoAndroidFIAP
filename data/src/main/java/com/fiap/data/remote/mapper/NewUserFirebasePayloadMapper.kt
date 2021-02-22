package com.fiap.data.remote.mapper

import com.fiap.data.remote.models.NewUserFirebasePayload
import com.hitg.domain.entity.NewUser
import com.hitg.domain.entity.User

object NewUserFirebasePayloadMapper {

    fun mapToNewUser(newUserFirebasePayload: NewUserFirebasePayload) = NewUser(
        name = newUserFirebasePayload.name ?: "",
        email = newUserFirebasePayload.email ?: "",
        phone = newUserFirebasePayload.phone ?: "",
        password = newUserFirebasePayload.password ?: ""
    )

    fun mapToNewUserFirebasePayload(newUser: NewUser) = NewUserFirebasePayload(
        name = newUser.name,
        email = newUser.email,
        phone = newUser.phone,
        password = newUser.password
    )

    fun mapToUser(newUserFirebasePayload: NewUserFirebasePayload) = User(
        name = newUserFirebasePayload.name ?: ""
    )
}