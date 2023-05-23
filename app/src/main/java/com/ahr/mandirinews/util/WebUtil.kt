package com.ahr.mandirinews.util

import android.content.Context
import androidx.annotation.ColorInt
import androidx.browser.customtabs.CustomTabColorSchemeParams
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.net.toUri

fun Context.launchBrowser(url: String, @ColorInt colorPrimary: Int) {
    val customTabsIntent = CustomTabsIntent.Builder()
        .setDefaultColorSchemeParams(
            CustomTabColorSchemeParams.Builder()
                .setToolbarColor(colorPrimary)
                .build()
        )
        .setColorSchemeParams(
            CustomTabsIntent.COLOR_SCHEME_DARK,
            CustomTabColorSchemeParams.Builder()
                .setToolbarColor(colorPrimary)
                .build()
        )
        .build()
    customTabsIntent.launchUrl(this, url.toUri())
}