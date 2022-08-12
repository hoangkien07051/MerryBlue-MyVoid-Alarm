package com.merryblue.myvoicealarm.common.extenstion

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatImageView
import com.bumptech.glide.Glide
import com.merryblue.myvoicealarm.R


fun ImageView.loadImage(url: String?) {
    Glide.with(this)
        .load(url)
        .into(this)
}

fun ImageView.loadImageDrawable(drawable: Drawable?) {
    Glide.with(this)
        .load(drawable)
        .into(this)
}

fun AppCompatImageView.loadImage(url: String?) {
    Glide.with(this)
        .load(url)
        .into(this)
}

fun AppCompatImageView.loadImageCache(url: String?) {
    if (url.isNullOrEmpty()) {
        this.setImageResource(R.drawable.ic_launcher_background)
    } else {
        Glide.with(this)
            .load(url)
            .into(this)
    }
}



