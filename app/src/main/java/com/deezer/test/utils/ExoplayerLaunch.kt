package com.deezer.test.utils

import android.content.Context
import android.net.Uri
import com.deezer.test.R
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util

class ExoplayerLaunch(val context: Context) {

   init {
       initExoplayerBuilder()
   }
     var player: SimpleExoPlayer? = null

    val userAgent =
        Util.getUserAgent(
            context,
            context.getString(R.string.app_name)
        )

    fun initExoplayerBuilder(): SimpleExoPlayer {
        player = SimpleExoPlayer.Builder(context).build()
        return player as SimpleExoPlayer
    }

    fun buildMediaSource(uri: Uri): MediaSource? {
        val dataSourceFactory: DataSource.Factory =
            DefaultDataSourceFactory(context, userAgent)
        return ProgressiveMediaSource.Factory(dataSourceFactory)
            .createMediaSource(uri)
    }


}