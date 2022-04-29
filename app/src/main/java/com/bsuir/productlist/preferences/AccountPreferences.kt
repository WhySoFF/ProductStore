package com.bsuir.productlist.preferences

import android.content.SharedPreferences

class AccountPreferences(private val preferences: SharedPreferences) {

    companion object {
        private const val USER_NAME_PREF_KEY = "USER_NAME_PREF_KEY"
        private const val USER_EMAIL_PREF_KEY = "USER_EMAIL_PREF_KEY"
    }

    var userName: String
        get() = preferences.getString(USER_NAME_PREF_KEY, "") ?: ""
        set(value) = preferences.edit().putString(USER_NAME_PREF_KEY, value).apply()

    var userEmail: String
        get() = preferences.getString(USER_EMAIL_PREF_KEY, "") ?: ""
        set(value) = preferences.edit().putString(USER_EMAIL_PREF_KEY, value).apply()

}