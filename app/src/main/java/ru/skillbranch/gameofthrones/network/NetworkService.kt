package ru.skillbranch.gameofthrones.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetworkService  {
    private const val BASE_URL = "https://jsonplaceholder.typicode.com"
    private val retrofit: Retrofit

    init {
        retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun getJSONApi(): JSONPlaceHolderApi {
        return retrofit.create(JSONPlaceHolderApi::class.java)
    }
}