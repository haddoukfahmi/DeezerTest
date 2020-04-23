package com.deezer.test.data.model

import com.google.gson.annotations.SerializedName


data class Alternative(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("tracklist") val alternativeTracklist: String
)