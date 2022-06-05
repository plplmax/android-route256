package dev.ozon.gitlab.plplmax.homework2.presentation.core

import android.widget.ImageView
import com.bumptech.glide.Glide

fun ImageView.load(url: String) {
    Glide.with(this)
        .load(url)
        .into(this)
}