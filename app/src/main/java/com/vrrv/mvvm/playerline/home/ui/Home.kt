package com.vrrv.mvvm.playerline.home.ui

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.vrrv.mvvm.playerline.home.HomeViewModel
import com.vrrv.mvvm.playerline.ui.theme.PLTextView
import com.vrrv.mvvm.playerline.ui.theme.ShimmerItem

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
        LoadScreenData(viewModel = viewModel)
    }
}

@Composable
fun LoadScreenData(viewModel: HomeViewModel) {
    val tabIndex = viewModel.tabIndex.observeAsState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .draggable(
                state = viewModel.dragState.value!!,
                orientation = Orientation.Horizontal,
                onDragStarted = { },
                onDragStopped = { viewModel.updateTabIndexBasedOnSwipe() }
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) {
        when (tabIndex.value) {
            0 -> LoadPlayers(viewModel = viewModel)
            1,2 -> LoadEmptyContent()
        }
    }
}

@Composable
fun LoadPlayers(viewModel: HomeViewModel) {
    val playerData = viewModel.playerDetails.observeAsState()
    val isLoading = viewModel.isLoading.observeAsState()
    Column {
        if (isLoading.value == true) {
            repeat(10) {
                ShimmerAnimation()
                Divider(
                    color = Color.Gray,
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth()
                        .width(1.dp)
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(0.dp)
            ) {
                playerData.value?.let {
                    items(it) { player ->
                        Row(
                            modifier = Modifier
                                .align(Alignment.Start)
                                .padding(horizontal = 16.dp, vertical = 12.dp)
                        ) {
                            Image(
                                painter = rememberImagePainter(player.player_image_url),
                                contentDescription = null,
                                modifier = Modifier.size(70.dp)
                            )
                            Column {
                                Row {
                                    PLTextView(
                                        data = player.pname,
                                        size = 14,
                                        modifier = Modifier.padding(0.dp)
                                    )
                                    PLTextView(
                                        data = player.position,
                                        size = 12,
                                        modifier = Modifier.padding(top = 2.dp, start = 12.dp)
                                    )
                                }
                                PLTextView(
                                    data = player.title,
                                    modifier = Modifier.padding(top = 4.dp)
                                )
                                PLTextView(
                                    data = player.details,
                                    weight = FontWeight.Thin,
                                    modifier = Modifier.padding(top = 4.dp)
                                )
                            }
                        }
                        Divider(
                            color = Color.Gray,
                            modifier = Modifier
                                .padding(horizontal = 16.dp)
                                .fillMaxWidth()
                                .width(1.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun LoadEmptyContent() {
    Column {
        Row(modifier = Modifier.align(Alignment.CenterHorizontally)) {
            PLTextView(
                data = "No data available at this moment",
                size = 14,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
}

@Composable
fun ShimmerAnimation() {
    val transition = rememberInfiniteTransition()
    val translateAnim by transition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            tween(durationMillis = 1200, easing = FastOutSlowInEasing),
            RepeatMode.Reverse
        )
    )

    val brush = Brush.linearGradient(
        colors = listOf(
            Color.LightGray.copy(0.9f),
            Color.LightGray.copy(0.2f),
            Color.LightGray.copy(0.9f)
        ),
        start = Offset(10f, 10f),
        end = Offset(translateAnim, translateAnim)
    )
    ShimmerItem(brush = brush)
}
