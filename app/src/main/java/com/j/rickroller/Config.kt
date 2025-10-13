package com.j.rickroller

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager

object Config {
    const val KEY_ONLINE: String = "pref_online"
    const val KEY_CUSTOM_URL_PREF: String = "pref_custom_url"
    const val KEY_CUSTOM_URL: String = "pref_url"
    const val DEFAULT_URL: String = "https://dot166.github.io/coolvideo.mp4"

    fun getOnlineMode(context: Context): Boolean {
        val prefs: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        val onlineMode: Boolean = prefs.getBoolean(KEY_ONLINE, false)
        return onlineMode
    }

    private fun getCustomUrlPref(context: Context): Boolean {
        val prefs: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        val customUrlPref: Boolean = prefs.getBoolean(KEY_CUSTOM_URL_PREF, false)
        return customUrlPref
    }

    private fun getCustomUrl(context: Context): String? {
        val prefs: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        val customUrl: String? = prefs.getString(KEY_CUSTOM_URL, null)
        return if (customUrl == "") {
            null
        } else {
            customUrl
        }
    }

    fun getURL(context: Context): String? {
        return if (getCustomUrlPref(context) && getCustomUrl(context) != null) {
            getCustomUrl(context)
        } else {
            DEFAULT_URL
        }
    }
}
