package com.example.sololearntesttask

import androidx.annotation.Keep
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@Keep
interface ApiResultCallback<T> {
    fun onSuccess(response: T?)

    fun onError(errorString: String?) {
    }

}


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
    resultCallBack: ApiResultCallback<T>? = null,
    apiFunction: suspend () -> Response<T>,
) {

    withContext(
        Dispatchers.IO + BaseCoroutineExceptionHandler(
            CoroutineExceptionHandler,
            resultCallBack
        )
    ) {
        val response = apiFunction()
        val responseBody = response.body()

        GitRepoApp.mCurrentFragment?.lifecycleScope?.launch(
            Dispatchers.Main + BaseCoroutineExceptionHandler(
                CoroutineExceptionHandler, resultCallBack
            )
        ) {
            if (response.isSuccessful) {
                resultCallBack?.onSuccess(responseBody)
            } else {

                val errorString = response.errorBody()?.string()

                resultCallBack?.onError(errorString)


            }
        }
    }
}


