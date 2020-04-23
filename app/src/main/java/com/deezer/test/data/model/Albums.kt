package com.deezer.test.data.model

import com.google.gson.annotations.SerializedName

data class Albums(
    @SerializedName("data") val data: List<Data>,
    @SerializedName("checksum") val checksum: String,
    @SerializedName("total") val total: Int,
    @SerializedName("next") val next: String
)