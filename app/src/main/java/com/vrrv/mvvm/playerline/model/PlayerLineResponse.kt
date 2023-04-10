package com.vrrv.mvvm.playerline.model

data class Players(val result: String, val data: PlayersList)

data class PlayersList(val newslist: List<Player>)

data class Player(
    val id: String,
    val pname: String,
    val position: String,
    val title: String,
    val details: String,
    val player_image_url: String
)


