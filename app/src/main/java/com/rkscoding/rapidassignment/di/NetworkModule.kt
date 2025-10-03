package com.rkscoding.rapidassignment.di

import android.content.Context
import com.rkscoding.rapidassignment.data.remote.api.AuthApiService
import com.rkscoding.rapidassignment.data.remote.api.QuizApiService
import com.rkscoding.rapidassignment.data.remote.api.UserProfileApiService
import com.rkscoding.rapidassignment.data.remote.reposotory.QuizRepository
import com.rkscoding.rapidassignment.data.remote.session.SessionToken
import com.rkscoding.rapidassignment.data.remote.reposotory.UsersAuthRepository
import com.rkscoding.rapidassignment.data.remote.reposotory.UserProfileRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    // This function provides a customized OkHttpClient instance using Hilt's @Provides annotation
    @Provides
    fun provideHttpClient(sessionToken: SessionToken): OkHttpClient {
        // Create a new OkHttpClient builder
        val client = OkHttpClient.Builder()
        // interceptor to automatically include an Authorization header with each request
        client.addInterceptor { chain ->
            val token = sessionToken.getToken() ?: ""
            val request = chain.request().newBuilder()
                // token from session storage to the request headers
                .addHeader("X-Session-Token", token)
                .build()
            // Proceed with the request using the modified request
            chain.proceed(request)
        }

        // logging interceptor for debugging purposes (optional)
        client.addInterceptor(
            HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })

        // Build and return the customized OkHttpClient instance
        return client.build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
//            .baseUrl("http://10.162.73.186:8080/")
//            .baseUrl("http://127.0.0.1:8080/")
            .baseUrl("http://10.110.44.186:8080")
//            .baseUrl("http://192.168.156.186:8080")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideAuthApiService(retrofit: Retrofit): AuthApiService {
        return retrofit.create(AuthApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideAuthRepository(apiService: AuthApiService): UsersAuthRepository {
        return UsersAuthRepository(apiService)
    }

    @Provides
    @Singleton
    fun provideQuizApiService(retrofit: Retrofit) : QuizApiService {
        return retrofit.create(QuizApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideQuizRepository(apiService: QuizApiService) : QuizRepository {
        return QuizRepository(apiService)
    }

    @Provides
    @Singleton
    fun provideUserProfileApiService(retrofit: Retrofit) : UserProfileApiService {
        return retrofit.create(UserProfileApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideUserProfileRepository(apiService: UserProfileApiService) : UserProfileRepository {
        return UserProfileRepository(apiService)
    }

    @Provides
    fun provideSession(@ApplicationContext context: Context): SessionToken {
        return SessionToken(context)
    }
}