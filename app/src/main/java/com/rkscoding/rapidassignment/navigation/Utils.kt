package com.rkscoding.rapidassignment.navigation

import kotlinx.serialization.json.Json

object NavSerializer{
    @PublishedApi
    internal val json = Json {
        ignoreUnknownKeys = true
        encodeDefaults = true
    }

    inline fun <reified T> encode(data : T) : String {
        return json.encodeToString(data)
    }

    inline fun <reified T> decode(jsonString : String) : T {
        return json.decodeFromString(jsonString)
    }
}