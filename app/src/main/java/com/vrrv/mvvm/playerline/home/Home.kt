package com.vrrv.mvvm.playerline.home

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState

@Composable
fun TabLayout(viewModel: HomeViewModel) {
    val tabIndex = viewModel.tabIndex.observeAsState()
    Column(modifier = Modifier.fillMaxWidth()) {
        TabRow(selectedTabIndex = tabIndex.value!!) {
            viewModel.tabs.forEachIndexed { index, title ->
                Tab(text = { Text(title) },
                    selected = tabIndex.value!! == index,
                    onClick = { viewModel.updateTabIndex(index) },
                )
            }
        }
        when (tabIndex.value) {
            0 -> HomeScreen(viewModel = viewModel)
            1,2 -> LoadEmptyDataContent(viewModel = viewModel)
        }
    }
}


@Composable
fun HomeScreen(viewModel: HomeViewModel) {
    Column(modifier = Modifier.fillMaxSize().draggable(
        state = viewModel.dragState.value!!,
        orientation = Orientation.Horizontal,
        onDragStarted = {  },
        onDragStopped = {
            viewModel.updateTabIndexBasedOnSwipe()
        }),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) {
        Row(modifier = Modifier.align(Alignment.CenterHorizontally)) {
            Text(
                text = "Welcome Home!",
                textAlign = TextAlign.Center,
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal
            )
        }
    }
}

@Composable
fun LoadEmptyDataContent(viewModel: HomeViewModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .draggable(
                state = viewModel.dragState.value!!,
                orientation = Orientation.Horizontal,
                onDragStarted = {  },
                onDragStopped = { viewModel.updateTabIndexBasedOnSwipe() }
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) {
        Row(modifier = Modifier.align(Alignment.CenterHorizontally)) {
            Text(
                text = "No data available at this moment",
                textAlign = TextAlign.Center,
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal
            )
        }
    }
}
