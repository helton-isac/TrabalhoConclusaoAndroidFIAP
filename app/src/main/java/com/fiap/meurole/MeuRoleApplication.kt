package com.fiap.meurole

import android.app.Application
import com.fiap.data.di.dataModules
import com.fiap.meurole.di.presentationModule
import com.google.android.libraries.places.api.Places
import com.hitg.domain.di.domainModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import java.util.*

class MeuRoleApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MeuRoleApplication)
            modules(domainModule + dataModules + listOf(presentationModule))
        }

        Places.initialize(this@MeuRoleApplication, resources.getString(R.string.google_maps_key))
    }
}