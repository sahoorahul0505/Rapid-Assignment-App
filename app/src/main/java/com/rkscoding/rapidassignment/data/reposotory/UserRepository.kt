package com.rkscoding.rapidassignment.data.reposotory


import com.rkscoding.rapidassignment.data.common.Resource
import com.rkscoding.rapidassignment.data.model.request.RegisterRequest
import com.rkscoding.rapidassignment.data.model.response.GenericResponse
import com.rkscoding.rapidassignment.data.remote.api.ApiService
import javax.inject.Inject

class UserRepository @Inject constructor(private val apiService: ApiService){

    suspend fun register(request: RegisterRequest) : Resource<GenericResponse> {
        return try {
            val response = apiService.register(request)

            if (response.isSuccessful){
                // Body should not be null
                response.body()?.let { dto ->
                    Resource.Success(dto.toGenericResponse())
                } ?: Resource.Error("Empty response from server")
            }else{
                // Extract the error message from the response
                val errorMsg = response.errorBody()?.string() ?: "Unknown error"
                Resource.Error(errorMsg)
            }
        }catch (e : Exception){
            // handle exception like IOException, HttpException, etc
            Resource.Error(e.localizedMessage ?: "An unknown error occurred")
        }
    }
}