package com.deezer.test.data.network

import com.deezer.test.BuildConfig
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

object RetrofitClient {

    private fun cacheHttp(): Cache {
        val cacheSize: Long = 10 * 1024 * 1024
        val fileName = "data.txt"

        var file = File(fileName)
        return Cache(file, cacheSize)
    }

    fun CreateNetworkClient() = retrofitClient(
        BuildConfig.API_URL,
        provideOkHttpClient()
    )

    private fun retrofitClient(baseUrl: String, httpClient: OkHttpClient): Retrofit =
        Retrofit.Builder().baseUrl(baseUrl)
            .client(httpClient)
            .addConverterFactory(MoshiConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()

    fun provideOkHttpClient() = OkHttpClient.Builder()
        .addInterceptor(makeLoggingInterceptor())
        .cache(cacheHttp())
        .apply { setTimeOutToOkHttpClient(this) }
        .build()

    private fun setTimeOutToOkHttpClient(okHttpClientBuilder: OkHttpClient.Builder) =
        okHttpClientBuilder.apply {
            connectTimeout(1, TimeUnit.MINUTES)
            readTimeout(30, TimeUnit.SECONDS)
            writeTimeout(15, TimeUnit.SECONDS)
            build()
        }


    private fun makeLoggingInterceptor(): HttpLoggingInterceptor {
        val logging = HttpLoggingInterceptor()
        if (BuildConfig.DEBUG)
            logging.level =
                HttpLoggingInterceptor.Level.BODY
        return logging
    }
}