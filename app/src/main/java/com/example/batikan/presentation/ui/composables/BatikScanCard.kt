package com.example.batikan.presentation.ui.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.navigation.NavController
import com.example.batikan.R
import com.example.batikan.presentation.ui.theme.Primary600
import com.example.batikan.presentation.ui.theme.TextSmallRegular
import com.example.batikan.presentation.ui.theme.TextXlSemiBold
import com.example.batikan.presentation.ui.theme.White


@Composable
fun BatikScanCard(
    navController: NavController,
    modifier: Modifier = Modifier,
    backgroundColor: Color = Primary600,
    imageResource: Int = R.drawable.scan,
    title: String = stringResource(R.string.scan_title),
    description: String = stringResource(R.string.description_card_scan)
){
    Card (
        modifier = modifier
            .fillMaxWidth(),
        shape = RectangleShape
    ) {
        Row (
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .background(color = backgroundColor)
                .padding(vertical = 20.dp)
                .padding(horizontal = 20.dp)
                .clickable(onClick = {
                    navController.navigate("camera_screen")
                })

        ){
            Image(
                painter = painterResource(id = imageResource),
                contentDescription = null,
                modifier = Modifier
                    .size(60.dp)
            )
            Spacer(Modifier.width(20.dp))
            Column (
                modifier = Modifier
            ) {
                Text(
                    text = title,
                    style = TextXlSemiBold,
                    color = White
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    text = description,
                    style = TextSmallRegular,
                    color = White
                )
            }
        }
    }
}