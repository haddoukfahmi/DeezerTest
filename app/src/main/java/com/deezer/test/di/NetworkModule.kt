package com.deezer.test.di

import com.deezer.test.data.network.AlbumApi
import com.deezer.test.data.network.RetrofitClient
import com.deezer.test.data.network.TrackListApi
import org.koin.dsl.module
import retrofit2.Retrofit

val retrofit: Retrofit = RetrofitClient.CreateNetworkClient()
private val albumApi: AlbumApi = retrofit.create(AlbumApi::class.java)
private val trackListApi: TrackListApi = retrofit.create(TrackListApi::class.java)

val networkModule = module {
    single { albumApi }
    single { trackListApi }
}