package com.vrrv.mvvm.playerline.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun PLTextView(
    data: String,
    size: Int = 11,
    weight: FontWeight = FontWeight.Normal,
    modifier: Modifier
) {
    Text(
        text = data,
        textAlign = TextAlign.Left,
        fontSize = size.sp,
        fontWeight = weight,
        maxLines = 2,
        modifier = modifier
    )
}


@Composable
fun ShimmerItem(brush: Brush) {
    Column(modifier = Modifier.padding(16.dp)) {
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .size(55.dp)
                .background(brush = brush)
        )

    }
}