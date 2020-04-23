package com.deezer.test.data.model

import com.google.gson.annotations.SerializedName

data class Artiste(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("picture") val picture: String,
    @SerializedName("picture_small") val picture_small: String,
    @SerializedName("picture_medium") val picture_medium: String,
    @SerializedName("picture_big") val picture_big: String,
    @SerializedName("picture_xl") val picture_xl: String,
    @SerializedName("type") val type: String
)