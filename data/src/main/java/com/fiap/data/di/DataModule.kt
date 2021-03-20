package com.fiap.data.di

import android.content.Context
import com.fiap.data.local.datasource.BiometricsLocalDataSource
import com.fiap.data.local.datasource.BiometricsLocalDataSourceImpl
import com.fiap.data.remote.datasource.*
import com.fiap.data.repository.*
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.hitg.domain.repository.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

@ExperimentalCoroutinesApi
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
            Firebase.firestore,
            Firebase.analytics
        )
    }
    factory<RoadmapRemoteDataSource> {
        RoadmapRemoteFirebaseDataSourceImpl(
            Firebase.firestore
        )
    }
    factory<PointOfInterestRemoteDataSource> {
        PointOfInterestRemoteFirebaseDataSourceImpl(
            Firebase.firestore
        )
    }
    factory<RemoteConfigDataSource> {
        RemoteConfigDataSourceImpl(
            Firebase.remoteConfig
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
    factory<RoadmapRepository> {
        RoadmapRepositoryImpl(
            roadmapRemoteDataSource = get()
        )
    }
    factory<PointOfInterestRepository> {
        PointOfInterestRepositoryImpl(
            poiRemoteDataSource = get()
        )
    }
    factory<SloganRepository> {
        SloganRepositoryImpl(
            remoteConfigDataSource = get()
        )
    }
    factory<ToggleFeatureRepository> {
        ToggleFeatureRepositoryImpl(
            remoteConfigDataSource = get()
        )
    }
}

@ExperimentalCoroutinesApi
val dataModules = listOf(repositoryModule)