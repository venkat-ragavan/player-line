package com.vrrv.mvvm.playerline.domain

import com.vrrv.mvvm.playerline.model.Players
import retrofit2.Call
import retrofit2.http.GET

interface IPlayer {

    @GET("0")
    fun getPlayers() : Call<Players>
}