package com.example.batikan.presentation.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.batikan.presentation.ui.theme.Primary600

@Composable
fun IndicatorUI(
    pageSize: Int,
    currentPage: Int,
    selectedColor: Color = Primary600,
    unSelectedColor: Color = Color.Gray
) {
    Row(
        modifier = Modifier.padding(bottom = 16.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        repeat(pageSize) {
            Spacer(modifier = Modifier.size(2.5.dp))

            Box(modifier = Modifier
                .size(8.dp)
                .width(width = if (it == currentPage) 32.dp else 14.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(color = if(it == currentPage) selectedColor else unSelectedColor, shape = RoundedCornerShape(50))
                .padding(4.dp)
            )
            Spacer(modifier = Modifier.size(8.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun IndicatorUIPreview1() {
    IndicatorUI(pageSize = 2, currentPage = 0)
}

@Preview(showBackground = true)
@Composable
fun IndicatorUIPreview2() {
    IndicatorUI(pageSize = 2, currentPage = 1)
}
