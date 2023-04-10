package com.vrrv.mvvm.playerline.home

import android.app.Application
import androidx.compose.foundation.gestures.DraggableState
import androidx.compose.runtime.Composable
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

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

    fun updateTabIndexBasedOnSwipe() {
        _tabIndex.value = when (isSwipeToTheLeft) {
            true -> Math.floorMod(_tabIndex.value!!.plus(1), tabs.size)
            false -> Math.floorMod(_tabIndex.value!!.minus(1), tabs.size)
        }
    }

    fun updateTabIndex(i: Int) {
        _tabIndex.value = i
    }

}

data class TabRowItem(val title: String, val screen: @Composable () -> Unit)