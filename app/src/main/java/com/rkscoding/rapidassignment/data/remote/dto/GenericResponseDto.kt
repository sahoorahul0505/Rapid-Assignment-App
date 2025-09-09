package com.rkscoding.rapidassignment.data.remote.dto

import com.rkscoding.rapidassignment.data.model.response.GenericResponse

data class GenericResponseDto(
    val success : Boolean,
    val message : String
) {
    fun toGenericResponse() : GenericResponse {
        return GenericResponse(
            success = success,
            message = message
        )
    }
}
