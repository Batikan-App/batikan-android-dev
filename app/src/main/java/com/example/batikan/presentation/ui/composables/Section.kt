package com.example.batikan.presentation.ui.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.batikan.R
import com.example.batikan.presentation.ui.theme.DisplayXsSemiBold
import com.example.batikan.presentation.ui.theme.Gray400
import com.example.batikan.presentation.ui.theme.Primary600
import com.example.batikan.presentation.ui.theme.Secondary500
import com.example.batikan.presentation.ui.theme.TextLgMedium
import com.example.batikan.presentation.ui.theme.TextLgSemiBold
import com.example.batikan.presentation.ui.theme.TextMdSemiBold
import com.example.batikan.presentation.ui.theme.TextPrimary
import com.example.batikan.presentation.ui.theme.TextSecondary
import com.example.batikan.presentation.ui.theme.TextSmallMedium
import com.example.batikan.presentation.ui.theme.TextSmallRegular
import com.example.batikan.presentation.ui.theme.TextSmallSemiBold
import com.example.batikan.presentation.ui.theme.TextXlSemiBold
import com.example.batikan.presentation.ui.theme.TextXsRegular
import com.example.batikan.presentation.ui.theme.White


@Composable
fun GreetingSection(
    modifier: Modifier = Modifier,
    userName: String
){
    Row(
        modifier = modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically

    ){
        Column {
            Text(
                text = "Selamat datang,",
                style = TextXsRegular,
                color = TextSecondary
            )
            Text(
                text = userName,
                style = TextSmallMedium,
                color = TextPrimary
            )
        }
        Button(
            onClick = {},
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent,
                contentColor = TextPrimary
            ),
            contentPadding = PaddingValues(0.dp),
            elevation = null
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_notification),
                contentDescription = null
            )
        }
    }
}

@Composable
fun ProfileCard (
    modifier: Modifier = Modifier,
    imageResource: Int,
    name: String,
    email: String,
    phoneNumber: String,
    onActionClick: () -> Unit
) {
    Row (
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ){
        Row ( horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            Image(
                painter = painterResource(id = imageResource),
                contentDescription = "profile image",
                modifier = Modifier.size(width = 36.dp , height = 36.dp)
            )
            Column (
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = name,
                    style = TextMdSemiBold,
                    color = TextPrimary
                )
                Text(
                    text = email,
                    style = TextSmallRegular,
                    color = TextSecondary
                )
                Text(
                    text = phoneNumber,
                    style = TextSmallRegular,
                    color = TextSecondary
                )
            }
        }

        IconButton(
            onClick = onActionClick
        ) {
            Icon(imageVector = Icons.Default.Edit, contentDescription = null, modifier = Modifier.size(24.dp))
        }

    }
}

@Composable
fun SectionTitle(
    modifier: Modifier = Modifier,
    title: String,
    description: String,
    actionText: String? = null,
    onActionClick: (() -> Unit)? = null
){
    Row (
        modifier = modifier
            .fillMaxWidth(),
        horizontalArrangement = if (actionText != null && onActionClick != null) {
            Arrangement.SpaceBetween
        } else {
            Arrangement.Start
        },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column() {
            Text(
                text = title,
                style = TextLgSemiBold,
                color = TextPrimary,
            )
            Text(
                text = description,
                style = TextSmallRegular,
                color = TextSecondary
            )
        }
        if (actionText != null && onActionClick != null) {
            Button(
                onClick = onActionClick,
                shape = RectangleShape,
                modifier = Modifier.padding(start = 8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent,
                    contentColor = Primary600
                ),
                contentPadding = PaddingValues(0.dp),
                elevation = null

            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = actionText,
                        style = TextSmallSemiBold,
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Image(
                        painter = painterResource(id = R.drawable.ic_arrow),
                        contentDescription = null
                    )
                }
            }
        }
    }
}


@Composable
fun ProductNamePrice(
    modifier: Modifier  = Modifier,
    name: String,
    price: Int
){
    Column (
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Secondary500)
            .padding(horizontal = 30.dp, vertical = 20.dp)
    ) {
        Text(
            text = name,
            style = DisplayXsSemiBold,
            color = White
        )
        Spacer(Modifier.height(4.dp))
        Text(
            text = "Rp.${price}",
            style = TextLgMedium,
            color = White
        )
    }
}


@Composable
fun ProductPreview(
    modifier: Modifier = Modifier,
    imageResource: String
){
    Column (
        modifier = modifier
            .fillMaxWidth()
    ) {
        AsyncImage(
            model = imageResource,
            contentDescription = null,
            modifier = Modifier
                .height(425.dp)
                .fillMaxWidth(),
            contentScale = ContentScale.Crop
        )
    }
}


@Composable
fun ProductStatistic(
    modifier: Modifier = Modifier,
    sold: Int,
    stock: Int,
    type: String,
){
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 20.dp)
    ){
        Text(
            text = "Terjual ${sold} ",
            style = TextSmallRegular,
            color = TextSecondary
        )

        Spacer(Modifier.weight(1f))

        Text(
            text = " | ",
            style = TextSmallRegular,
            color = Gray400
        )

        Spacer(Modifier.weight(1f))

        Text(
            text = "Stok ${stock} ",
            style = TextSmallRegular,
            color = TextSecondary
        )

        Spacer(Modifier.weight(1f))

        Text(
            text = " | ",
            style = TextSmallRegular,
            color = Gray400
        )

        Spacer(Modifier.weight(1f))

        Text(
            text = type,
            style = TextSmallRegular,
            color = TextSecondary
        )
    }
}


@Composable
fun InfoSection(
    modifier: Modifier = Modifier,
    title: String,
    description: String
){
    Column () {
        Text(
            text = title,
            style = TextSmallMedium,
            color = TextPrimary
        )
        Text(
            text = description,
            style = TextSmallRegular,
            color = TextSecondary,
            textAlign = TextAlign.Justify
        )
    }

}

@Composable
fun ProductDetail(
    modifier: Modifier = Modifier,
    motifDescription: String,
    batikOrigin : String

){
    Column (
        modifier = modifier
    ) {
        Spacer(Modifier.height(20.dp))
        InfoSection(
            title = "Tentang Motif",
            description = motifDescription
        )
        Spacer(Modifier.height(20.dp))
        InfoSection(
            title = "Asal Batik",
            description = batikOrigin
        )
    }
}

@Composable
fun PageTitle(
    modifier: Modifier = Modifier,
    title: String,
    description: String,
){
    Column(
        modifier = modifier
    ){
        Text(
            text = title,
            style = TextXlSemiBold,
            color = TextPrimary,
        )
        Text(
            text = description,
            style = TextSmallRegular,
            color = TextSecondary,
        )
    }
}

@Composable
fun AboutBatik(
    modifier: Modifier = Modifier,
    description: String
){
    Column (
        modifier = modifier
    ) {
        Text(
            text = "Tentang Batik",
            style = TextSmallMedium,
            color = TextPrimary
        )
        Text(
            text = description,
            style = TextSmallRegular,
            color = TextSecondary,
            textAlign = TextAlign.Justify
        )
    }
}

