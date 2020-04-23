package com.deezer.test.data.repository

import com.deezer.test.data.model.TrackList
import com.deezer.test.data.network.TrackListApi

class TrackListRepository(private val trackListApi: TrackListApi) : BaseRepository() {

    suspend fun getTrackList(albumId: String): TrackList {

        val trackListResponse = safeApiCall(
            call = { trackListApi.getTracksLists(albumId).await() },
            errorMessage = "Oops something is wrong ..!!"
        )
        return trackListResponse!!
    }
}