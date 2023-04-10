package com.vrrv.mvvm.playerline.domain

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetroAPI {

    private const val BASE_URL = "https://www.playerline.org/test/static-endpoint/api/newslist/"

    val getData : IPlayer
        get() {
            val gson = GsonBuilder().setLenient().create()
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()

            return retrofit.create(IPlayer::class.java)
        }

}