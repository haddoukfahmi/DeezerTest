package com.deezer.test.data.model

import com.google.gson.annotations.SerializedName


data class Data(
    val id: Int,
    val title: String,
    val link: String,
    val cover: String,
    val cover_small: String,
    val cover_medium: String,
    val cover_big: String,
    val cover_xl: String,
    val nb_tracks: Int,
    val release_date: String,
    val record_type: String,
    val available: Boolean,
    val albumTracklist: String?,
    val explicit_lyrics: Boolean,
    val time_add: Int,
    val artist: Artiste,
    @SerializedName("alternative") val alternative: Alternative,
    val type: String
)