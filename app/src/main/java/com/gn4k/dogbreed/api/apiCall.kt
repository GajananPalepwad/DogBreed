package com.gn4k.dogbreed.api

import com.gn4k.dogbreed.dataClass.DogApiResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface DogApi {
    @GET("breed/hound/images")
    fun getHoundImages(): Call<DogApiResponse>
}



object RetrofitClient {
    private const val BASE_URL = "https://dog.ceo/api/"

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val dogApi: DogApi = retrofit.create(DogApi::class.java)
}