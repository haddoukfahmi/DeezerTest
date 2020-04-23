package com.deezer.test.di

import com.deezer.test.data.repository.AlbumRepository
import com.deezer.test.data.repository.TrackListRepository
import org.koin.dsl.module

val repositoryModule = module {
    single { AlbumRepository(get()) }
    single { TrackListRepository(get()) }
}