package com.example.films.util

import android.content.Context
import android.content.res.Configuration


object ColorUtils {

    fun isDarkTheme(context: Context): Boolean = (context.resources.configuration.uiMode
            and Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_YES

}