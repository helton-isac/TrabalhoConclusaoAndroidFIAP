package com.fiap.data.remote.mapper

import com.fiap.data.remote.models.NewUserFirebasePayload
import com.hitg.domain.entity.NewUser
import com.hitg.domain.entity.User

object NewUserFirebasePayloadMapper {

    fun mapToNewUserFirebasePayload(newUser: NewUser) = NewUserFirebasePayload(
        name = newUser.name,
        email = newUser.email,
        password = newUser.password
    )

    fun mapToUser(newUserFirebasePayload: NewUserFirebasePayload) = User(
        name = newUserFirebasePayload.name ?: ""
    )
}