package com.vrrv.mvvm.playerline.home.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.vrrv.mvvm.playerline.home.HomeViewModel
import com.vrrv.mvvm.playerline.ui.theme.PLTextView

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
    val playerData = viewModel.playerDetails.observeAsState()
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
        verticalArrangement = Arrangement.Top) {
        Row(
            modifier = Modifier
                .align(Alignment.Start)
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            Image(
                painter = rememberImagePainter(playerData.value?.player_image_url),
                contentDescription = null,
                modifier = Modifier.size(70.dp)
            )
            Column {
                Row {
                    PLTextView(
                        data = playerData.value?.pname.orEmpty(),
                        size = 14,
                        modifier = Modifier.padding(0.dp)
                    )
                    PLTextView(
                        data = playerData.value?.position.orEmpty(),
                        size = 12,
                        modifier = Modifier.padding(top = 2.dp, start = 12.dp)
                    )
                }
                PLTextView(
                    data = playerData.value?.title.orEmpty(),
                    modifier = Modifier.padding(top = 4.dp)
                )
                PLTextView(
                    data = playerData.value?.details.orEmpty(),
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

@Composable
fun LoadEmptyDataContent(viewModel: HomeViewModel) {
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
        Row(modifier = Modifier.align(Alignment.CenterHorizontally)) {
            PLTextView(
                data = "No data available at this moment",
                size = 14,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
}
