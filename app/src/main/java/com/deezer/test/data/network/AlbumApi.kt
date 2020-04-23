package com.deezer.test.data.network

import com.deezer.test.data.model.Albums
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET

interface AlbumApi {

    @GET("user/2529/albums")
    fun getAlbum(): Deferred<Response<Albums>>
}