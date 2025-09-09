package com.rkscoding.rapidassignment.data.remote.session

import android.content.Context
import androidx.core.content.edit

class SessionToken(val context: Context) {
    val sharedPreferences = context.getSharedPreferences("SessionToken", Context.MODE_PRIVATE)

    fun storeToken(token: String) {
        sharedPreferences.edit {
            putString("token", token)
        }
    }

    fun getToken() : String? {
        sharedPreferences.getString("token", null)?.let { token ->
            return token
        }
        return sharedPreferences.getString("token", null)
    }
}