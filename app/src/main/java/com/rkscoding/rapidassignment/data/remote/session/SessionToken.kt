package com.rkscoding.rapidassignment.data.remote.session

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

class SessionToken(context: Context) {
    val sharedPreferences : SharedPreferences = context.getSharedPreferences("SessionToken", Context.MODE_PRIVATE)

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