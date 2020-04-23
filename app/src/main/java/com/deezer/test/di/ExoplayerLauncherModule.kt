package com.deezer.test.di

import com.deezer.test.utils.ExoplayerLaunch
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module


val exoplayerLaunchModule = module {

    single { ExoplayerLaunch(androidContext()) }
}