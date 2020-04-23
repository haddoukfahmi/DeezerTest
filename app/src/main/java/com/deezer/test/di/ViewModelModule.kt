package com.deezer.test.di

import com.deezer.test.viewmodel.AlbumViewModel
import com.deezer.test.viewmodel.TrackListViewModel
import org.koin.dsl.module

val viewmodelModule = module {
    single { AlbumViewModel(get()) }

    factory { TrackListViewModel(get()) }


}