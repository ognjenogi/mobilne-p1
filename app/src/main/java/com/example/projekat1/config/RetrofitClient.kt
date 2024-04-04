package com.example.projekat1.config

import CatApiService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "https://api.thecatapi.com/v1/"
    private const val API_KEY = "live_dt2v59CjuzvlklNKhSz3jWMroogd4mYec9p1MAdLordFetWUHVp2cBtw823jHKD5"

    private val client = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val newRequest = chain.request().newBuilder()
                .addHeader("X-API-Key", API_KEY)
                .build()
            chain.proceed(newRequest)
        }
        .build()

    fun create(): CatApiService {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(CatApiService::class.java)
    }
}
