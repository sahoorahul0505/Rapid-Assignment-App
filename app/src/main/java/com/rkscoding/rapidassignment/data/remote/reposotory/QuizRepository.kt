package com.rkscoding.rapidassignment.data.remote.reposotory

import com.rkscoding.rapidassignment.data.common.ApiResponse
import com.rkscoding.rapidassignment.data.common.toParseError
import com.rkscoding.rapidassignment.data.remote.api.QuizApiService
import com.rkscoding.rapidassignment.data.remote.dto.request.GetQuestionsRequest
import com.rkscoding.rapidassignment.data.remote.dto.request.QuizSubmissionRequest
import com.rkscoding.rapidassignment.data.remote.dto.response.QuizSubmitResponse
import com.rkscoding.rapidassignment.data.remote.dto.response.UserQuestionResponse
import retrofit2.HttpException
import javax.inject.Inject

class QuizRepository @Inject constructor(private val apiService: QuizApiService) {
    suspend fun getAllQuestionsForUser(request: GetQuestionsRequest): ApiResponse<List<UserQuestionResponse>> {
        return try {
            apiService.getAllQuestionsForUser(request)
        }catch (ex : HttpException){
            throw Exception(ex.toParseError())
        }catch (e : Exception){
            throw Exception(e.message ?: "Something went wrong")
        }
    }

    suspend fun submitQuizForUser(request : QuizSubmissionRequest) : ApiResponse<QuizSubmitResponse> {
        return try {
            apiService.submitQuizForUser(request)
        }catch (ex : HttpException){
            throw Exception(ex.toParseError())
        }catch (e : Exception){
            throw Exception(e.message ?: "Something went wrong")
        }
    }
}