package com.example.batikan.presentation.ui.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import com.example.batikan.presentation.ui.theme.Primary200
import com.example.batikan.presentation.ui.theme.Primary50
import com.example.batikan.presentation.ui.theme.TextMdSemiBold
import com.example.batikan.presentation.ui.theme.TextPrimary
import com.example.batikan.presentation.ui.theme.TextSecondary
import com.example.batikan.presentation.ui.theme.TextSmallRegular

@Composable
fun VisualTryOnCard(
    modifier: Modifier = Modifier,
    imageResource : Int = R.drawable.visual_tryon,
    backgroundColor: Color = Primary50,
    title : String = stringResource(R.string.visual_try_on_title),
    description: String = stringResource(R.string.visual_try_on_description)
){
    Card (
        modifier = modifier
            .fillMaxWidth()
            .border(2.dp, Primary200),
        shape = RectangleShape
    ){
        Row (
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .background(color = backgroundColor)
                .padding(horizontal = 20.dp, vertical = 10.dp)

        ){
            Image(
                painter = painterResource(id = imageResource),
                contentDescription = null,
                modifier = Modifier
                    .size(80.dp)
            )

            Spacer(Modifier.height(18.dp))

            Column (
                modifier = Modifier
                    .fillMaxSize(),
            ){
                Text(
                    text = title,
                    style = TextMdSemiBold,
                    color = TextPrimary
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    text = description,
                    style = TextSmallRegular,
                    color = TextSecondary
                )
            }
        }

    }

}