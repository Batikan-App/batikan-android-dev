package com.example.batikan.presentation.ui.composables

import androidx.annotation.Size
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.batikan.presentation.ui.theme.Primary200
import com.example.batikan.presentation.ui.theme.Primary600
import com.example.batikan.presentation.ui.theme.TextLgRegular
import com.example.batikan.presentation.ui.theme.TextSmallRegular

@Composable
fun ButtonUi(
    text: String = "Default",
    backgroundColor: Color = Color.White,
    textColor: Color = Primary600,
    textStyle: TextStyle = TextLgRegular,
    fontSize: Int = 14,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick, colors = ButtonDefaults.buttonColors(
            containerColor = backgroundColor, contentColor = textColor
        ),
        modifier = Modifier.size(width = 400.dp, height = 50.dp),
        shape = RectangleShape
    ) {
        Text(
            text = text, fontSize = fontSize.sp, style = textStyle
        )
    }
}

@Preview
@Composable
fun NextButton() {
    ButtonUi(
        text="Selanjutnya",
        backgroundColor = Primary600,
        textColor = Color.White
    ) {
    }
}

@Preview
@Composable
fun BackButton() {
    ButtonUi(
        text = "Sebelumnya",
        backgroundColor = Color.White,
        textColor = Color.Gray,
        textStyle = TextLgRegular,
        fontSize = 14
    ) {

    }
}

//Column {
//    Button(
//        onClick = {  },
//        modifier = Modifier.size(width = 400.dp, height = 50.dp),
//        shape = RectangleShape,
//        colors = ButtonDefaults.buttonColors(containerColor = Primary600
//        )
//    ) {
//        Text(text = "Selanjutnya", color = Color.White)
//    }
//
//    Button(
//        onClick = { },
//        modifier = Modifier.size(width = 400.dp, height = 50.dp),
//        shape = RectangleShape,
//        colors = ButtonDefaults.buttonColors(containerColor = Color.White)
//    ) {
//        Text(text = "Sebelumnya", color = Color.Gray)
//    }
//}