package com.vrrv.mvvm.playerline.ui.theme

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
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