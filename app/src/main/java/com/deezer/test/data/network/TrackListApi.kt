package com.deezer.test.data.network

import com.deezer.test.data.model.TrackList
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface TrackListApi {

    @GET("album/{id}/tracks")
    fun getTracksLists(@Path("id") albumId: String): Deferred<Response<TrackList>>
}