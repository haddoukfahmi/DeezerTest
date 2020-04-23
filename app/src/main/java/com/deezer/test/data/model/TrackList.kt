package com.deezer.test.data.model

import com.google.gson.annotations.SerializedName


data class TrackList(
    @SerializedName("data") val data : List<DataTrackList>,
    @SerializedName("total") val total : Int
)


data class DataTrackList (

    @SerializedName("id") val id : Int,
    @SerializedName("readable") val readable : Boolean,
    @SerializedName("title") val title : String,
    @SerializedName("title_short") val title_short : String,
    @SerializedName("title_version") val title_version : String,
    @SerializedName("isrc") val isrc : String,
    @SerializedName("link") val link : String,
    @SerializedName("duration") val duration : Int,
    @SerializedName("track_position") val track_position : Int,
    @SerializedName("disk_number") val disk_number : Int,
    @SerializedName("rank") val rank : Int,
    @SerializedName("explicit_lyrics") val explicit_lyrics : Boolean,
    @SerializedName("explicit_content_lyrics") val explicit_content_lyrics : Int,
    @SerializedName("explicit_content_cover") val explicit_content_cover : Int,
    @SerializedName("preview") val preview : String,
    @SerializedName("artist") val artist : Artist,
    @SerializedName("type") val type : String
)


data class Artist (

    @SerializedName("id") val id : Int,
    @SerializedName("name") val name : String,
    @SerializedName("tracklist") val tracklist : String,
    @SerializedName("type") val type : String
)