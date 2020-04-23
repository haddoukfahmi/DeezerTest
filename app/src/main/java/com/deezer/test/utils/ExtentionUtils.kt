package com.deezer.test.utils

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.deezer.test.R

val requestOption = RequestOptions()
    .placeholder(R.drawable.ic_offline)

fun ImageView.loadPicture(url: String) {
    Glide.with(context)
        .load(url)
        .diskCacheStrategy(DiskCacheStrategy.DATA)
        .apply(requestOption)
        .into(this)

}