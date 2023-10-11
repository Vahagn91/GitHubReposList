package com.example.sololearntesttask.network

import androidx.annotation.Keep
import com.example.sololearntesttask.BuildConfig
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Keep
sealed class ApiResult<out T>(
    val data: T? = null,
    val error: String? = null,
)

class Success<T>(data: T? = null) : ApiResult<T>(data = data)

class Failure(error: String?) : ApiResult<Nothing>(error = error)


inline fun <reified T> createWebService(baseUrl: String): T {

    val interceptor = HttpLoggingInterceptor()
    interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

    val token = BuildConfig.GITHUB_ACCESS_TOKEN
    val client: OkHttpClient = OkHttpClient.Builder().addInterceptor(interceptor)
        .addInterceptor { chain ->
            val request = chain.request().newBuilder()
                .addHeader("Authorization", "Bearer $token")
                .build()
            chain.proceed(request)
        }
        .build()

    val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    return retrofit.create(T::class.java)
}

suspend fun <T> coroutineResponseWithContext(
    apiFunction: suspend () -> Response<T>,
): ApiResult<T> {

    return withContext(
        Dispatchers.IO + BaseCoroutineExceptionHandler(
            CoroutineExceptionHandler
        )

    ) {
        val response = apiFunction()
        val responseBody = response.body()


        if (response.isSuccessful) {
            return@withContext Success(responseBody)
        } else {
            val errorString = response.errorBody()?.string()
            return@withContext Failure(errorString)
        }
    }

}


