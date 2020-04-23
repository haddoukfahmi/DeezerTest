package com.deezer.test

import android.app.Application
import com.deezer.test.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class DeezerApp : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@DeezerApp)
            modules(listOf(networkModule, repositoryModule, viewmodelModule, fragmentModule, exoplayerLaunchModule))
        }
    }
}