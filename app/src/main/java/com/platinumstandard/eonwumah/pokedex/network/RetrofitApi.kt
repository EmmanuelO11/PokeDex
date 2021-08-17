package com.platinumstandard.eonwumah.pokedex.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class RetrofitApi {
    companion object {

        private const val BASE_URL = "https://pokeapi.co/api/v2/"

        private val interceptor = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }
        private val client = OkHttpClient.Builder().addInterceptor(interceptor).build()

        fun getRetrofitInstance(): Retrofit = Retrofit.Builder()
            .client(client)
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }

}