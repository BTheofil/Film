package com.example.films.util

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.films.R
import com.example.films.retrofit.Retrofit

object ImageLoader {

    private val requestOption = RequestOptions()
        .placeholder(R.drawable.ic_launcher_background)
        .error(R.drawable.ic_launcher_background)

    fun loadImage(context: Context, image: String, targetView : ImageView) {
        Glide.with(context)
            .applyDefaultRequestOptions(requestOption)
            .load(Retrofit.IMAGE_STORAGE_BASE_URL.plus(image))
            .into(targetView)
    }
}
