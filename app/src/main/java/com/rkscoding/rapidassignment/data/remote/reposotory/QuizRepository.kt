package com.rkscoding.rapidassignment.data.remote.reposotory

import com.rkscoding.rapidassignment.data.common.ApiResponse
import com.rkscoding.rapidassignment.data.common.toParseError
import com.rkscoding.rapidassignment.data.remote.api.QuizApiService
import com.rkscoding.rapidassignment.data.remote.dto.request.AnswerRequest
import com.rkscoding.rapidassignment.data.remote.dto.response.QuizSubmitResponse
import com.rkscoding.rapidassignment.data.remote.dto.response.UserQuizQuestionResponse
import com.rkscoding.rapidassignment.data.remote.dto.response.UsersQuizDetailsResponse
import retrofit2.HttpException
import javax.inject.Inject

class QuizRepository @Inject constructor(private val apiService: QuizApiService) {

    suspend fun getQuizDetailsForUser(quizCode : String) : ApiResponse<UsersQuizDetailsResponse> {
        return try {
            apiService.getQuizForUser(quizCode)
        }catch (ex : HttpException){
            throw Exception(ex.toParseError())
        }catch (e : Exception){
            throw Exception(e.message ?: "Something went wrong")
        }
    }

    suspend fun getQuizQuestionsForUser(quizCode : String): ApiResponse<List<UserQuizQuestionResponse>> {
        return try {
            apiService.getQuizQuestionsForUser(quizCode)
        }catch (ex : HttpException){
            throw Exception(ex.toParseError())
        }catch (e : Exception){
            throw Exception(e.message ?: "Something went wrong")
        }
    }

    suspend fun submitQuizForUser(quizCode: String, request : List<AnswerRequest>) : ApiResponse<QuizSubmitResponse> {
        return try {
            apiService.submitQuizForUser(quizCode, request)
        }catch (ex : HttpException){
            throw Exception(ex.toParseError())
        }catch (e : Exception){
            throw Exception(e.message ?: "Something went wrong")
        }
    }

    suspend fun getAllSubmissions() : ApiResponse<List<QuizSubmitResponse>> {
        return try {
            apiService.getAllSubmissionsForUser()
        }catch (ex : HttpException){
            throw Exception(ex.toParseError())
        }catch (e : Exception){
            throw Exception(e.message ?: "Something went wrong")
        }
    }
}