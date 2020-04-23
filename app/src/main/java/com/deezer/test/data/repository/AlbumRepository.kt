package com.deezer.test.data.repository

import com.deezer.test.data.model.Albums
import com.deezer.test.data.network.AlbumApi

class AlbumRepository(private val albumApi: AlbumApi) : BaseRepository() {

    suspend fun getAlbums(): Albums {

        val albumResponse = safeApiCall(
            call = { albumApi.getAlbum().await() },
            errorMessage = "Oops something is wrong ..!!"
        )
        return albumResponse!!
    }
}