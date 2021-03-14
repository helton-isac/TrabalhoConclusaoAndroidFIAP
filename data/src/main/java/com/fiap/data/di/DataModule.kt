package com.fiap.data.di

import android.content.Context
import com.fiap.data.local.datasource.BiometricsLocalDataSource
import com.fiap.data.local.datasource.BiometricsLocalDataSourceImpl
import com.fiap.data.remote.datasource.UserRemoteDataSource
import com.fiap.data.remote.datasource.UserRemoteFirebaseDataSourceImpl
import com.fiap.data.repository.BiometricsRepositoryImpl
import com.fiap.data.repository.UserRepositoryImpl
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.hitg.domain.repository.BiometricsRepository
import com.hitg.domain.repository.UserRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val repositoryModule = module {
    factory<BiometricsLocalDataSource> {
        BiometricsLocalDataSourceImpl(
            sharedPreferences = androidContext().getSharedPreferences(
                "com.fiap.meurole.PREFERENCES",
                Context.MODE_PRIVATE
            )
        )
    }
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
    factory<BiometricsRepository> {
        BiometricsRepositoryImpl(
            biometricsLocalDataSource = get()
        )
    }
}

val dataModules = listOf(repositoryModule)