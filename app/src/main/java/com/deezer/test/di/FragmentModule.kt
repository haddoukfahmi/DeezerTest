package com.deezer.test.di

import com.deezer.test.ui.MainActivity
import com.deezer.test.ui.albumdetails.AlbumDetailsFragment
import com.deezer.test.ui.albumlist.AlbumFragmentList
import org.koin.androidx.fragment.dsl.fragment
import org.koin.dsl.module

val fragmentModule = module {

    scope<MainActivity> {
        fragment { AlbumFragmentList() }
        fragment { AlbumDetailsFragment() }
    }
}