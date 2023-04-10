package com.vrrv.mvvm.playerline.home

import android.app.Application
import android.util.Log
import androidx.compose.foundation.gestures.DraggableState
import androidx.compose.runtime.Composable
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.vrrv.mvvm.playerline.domain.RetroAPI
import com.vrrv.mvvm.playerline.model.Player
import com.vrrv.mvvm.playerline.model.Players
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel(application: Application): AndroidViewModel(application) {

    private val _tabIndex = MutableLiveData(0)
    val tabIndex: LiveData<Int> = _tabIndex

    val tabs = listOf("All", "Following", "Resources")

    var isSwipeToTheLeft: Boolean = false
    private val draggableState = DraggableState { delta ->
        isSwipeToTheLeft = delta > 0
    }

    private val _dragState = MutableLiveData<DraggableState>(draggableState)
    val dragState: LiveData<DraggableState> = _dragState

    val playerDetails = MutableLiveData<Player>()
    fun updateTabIndexBasedOnSwipe() {
        _tabIndex.value = when (isSwipeToTheLeft) {
            true -> Math.floorMod(_tabIndex.value!!.plus(1), tabs.size)
            false -> Math.floorMod(_tabIndex.value!!.minus(1), tabs.size)
        }
    }

    fun updateTabIndex(i: Int) {
        _tabIndex.value = i
    }

    fun loadPlayerDetails() {
        val call : Call<Players> = RetroAPI.getData.getPlayers()
        call.enqueue(
            object : Callback<Players> {
                override fun onResponse(call: Call<Players>, response: Response<Players>) {
                    response.body().let {
                        playerDetails.value = it?.data?.newslist?.first()
                        Log.d("vrrv response ", playerDetails.value.toString())
                    }
                }

                override fun onFailure(call: Call<Players>, t: Throwable) {}
            }
        )
    }

}

data class TabRowItem(val title: String, val screen: @Composable () -> Unit)