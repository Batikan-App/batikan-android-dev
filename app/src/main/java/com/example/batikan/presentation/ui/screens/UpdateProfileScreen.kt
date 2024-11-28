package com.example.batikan.presentation.ui.screens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.batikan.presentation.ui.theme.BatikanTheme
import com.example.batikan.presentation.ui.theme.Primary50
import com.example.batikan.presentation.ui.theme.Primary600
import com.example.batikan.presentation.ui.theme.TextMdSemiBold
import com.example.batikan.presentation.ui.theme.TextPrimary
import com.example.batikan.presentation.ui.theme.TextSmallSemiBold
import com.example.batikan.presentation.ui.theme.White


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateProfileContent(
    navController: NavController,
    modifier: Modifier = Modifier,
    name: String,
    email: String,
    phoneNumber: String
) {
    var name by remember { mutableStateOf(name)}
    var email by remember { mutableStateOf(email)}
    var phoneNumber by remember { mutableStateOf(phoneNumber)}

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Ubah Profile",
                        style = TextMdSemiBold,
                        color = TextPrimary
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.navigateUp()
                    }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = null,
                            tint = TextPrimary
                        )
                    }
                }
            )
        },
        bottomBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Primary50)
                    .padding(horizontal = 30.dp)
                    .padding(vertical = 20.dp),
                horizontalArrangement = Arrangement.SpaceBetween,

                ) {

                Button(
                    onClick = { },
                    modifier = modifier
                        .fillMaxWidth()
                        .height(40.dp),
                    shape = RectangleShape,
                    colors = ButtonDefaults.buttonColors(containerColor = Primary600)
                ) {
                    Text(
                        text = "Simpan",
                        style = TextSmallSemiBold,
                        color = White
                    )
                }


            }
        }
    ) { innerPadding ->
        LazyColumn (
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            item {
                TextFieldWithTitle(
                    title = "Nama",
                    value = name,
                    onValueChange = { name = it }
                )
            }

            item {
                TextFieldWithTitle(
                    title = "Email",
                    value = email,
                    onValueChange = { email = it }
                )
            }

            item {
                TextFieldWithTitle(
                    title = "Nomor Telephone",
                    value = phoneNumber,
                    onValueChange = { phoneNumber = it }
                )
            }
        }


    }
}

//@Preview(showBackground = true)
//@Composable
//fun UpdateProfileScreenPreview() {
//    BatikanTheme {
//        UpdateProfileContent(
//            name = "John Doe",
//            email = "johnn@batikan.com",
//            phoneNumber = "081234567890"
//        )
//    }
//}