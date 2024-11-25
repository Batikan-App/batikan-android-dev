package com.example.batikan.presentation.ui.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.batikan.R
import com.example.batikan.presentation.ui.theme.Primary600
import com.example.batikan.presentation.ui.theme.TextSmallRegular
import com.example.batikan.presentation.ui.theme.TextXlSemiBold
import com.example.batikan.presentation.ui.theme.White


@Composable
fun NewProductCard(
    modifier: Modifier = Modifier,
    backgroundColor: Color = Primary600,
    imageResource: Int = R.drawable.batik_new,
    title: String = stringResource(R.string.title_new_product),
    description: String = stringResource(R.string.description_new_product),
) {
    Card(
        modifier = modifier
            .fillMaxWidth(),
        shape = RectangleShape
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp)
                .background(color = backgroundColor)
        ) {
            Image(
                painter = painterResource(id = imageResource),
                contentDescription = null,
                modifier = Modifier
                    .width(186.dp)
                    .fillMaxHeight()
                    .align(Alignment.CenterVertically)
            )
            Column(
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .padding(start = 20.dp, end = 20.dp)
                    .fillMaxSize()
            ) {
                Text(
                    text = title,
                    style = TextXlSemiBold,
                    color = White,
                    modifier = Modifier
                )
                Spacer(Modifier.height(8.dp))
                Text(
                    text = description,
                    style = TextSmallRegular,
                    color = White,
                    modifier = Modifier
                )
            }
        }
    }
}