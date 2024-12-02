package com.example.batikan.presentation.ui.composables

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.batikan.presentation.ui.theme.Primary600
import com.example.batikan.presentation.ui.theme.TextLgRegular
import com.example.batikan.presentation.ui.theme.TextSecondary

@Composable
fun ButtonUi(
    text: String = "Default",
    isNext: Boolean = true,
    textStyle: TextStyle = TextLgRegular,
    fontSize: Int = 14,
    onClick: () -> Unit
) {
    val backgroundColor = if (isNext) Primary600 else Color.White
    val textColor = if (isNext) Color.White else TextSecondary

    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = backgroundColor,
            contentColor = textColor
        ),
        modifier = Modifier
            .fillMaxWidth()
            .size(width = 330.dp, 50.dp)
            .height(48.dp),
        shape = RectangleShape
    ) {
        Text(
            text = text,
            fontSize = fontSize.sp,
            style = textStyle
        )
    }
}