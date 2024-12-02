package com.example.batikan.presentation.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.batikan.R
import com.example.batikan.presentation.ui.theme.BatikanTheme
import com.example.batikan.presentation.ui.theme.DisplayXsBold
import com.example.batikan.presentation.ui.theme.Primary600

@Composable
fun RegisterScreen(
    navController: NavController,
    ) {
    var isButtonClicked by remember { mutableStateOf(false) }

    var name by remember { mutableStateOf("")}
    var email by remember { mutableStateOf("")}
    var phoneNumber by remember { mutableStateOf("")}
    var password by remember { mutableStateOf("")}
    var passwordVerify by remember { mutableStateOf("")}

    val isFormValid = name.isNotBlank() && email.isNotBlank() && phoneNumber.isNotBlank() && password.isNotBlank() && passwordVerify.isNotBlank()

    Column(
        modifier = Modifier.fillMaxSize().background(Color.White)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.auth_header),
                contentDescription = "Background Batikan",
                modifier = Modifier
                    .size(width = 410.dp, height = 230.dp),
                contentScale = ContentScale.FillHeight
            )

            Column {
                Image(
                    painter = painterResource(id = R.drawable.batikan),
                    contentDescription = "Logo Batikan",
                    modifier = Modifier
                        .padding(top = 54.dp)
                        .size(width = 220.dp, height = 120.dp)
                )
            }
        }

        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Buat akun",
                style = DisplayXsBold
            )

            Text(
                text = "Selamat datang calon pemerhati sosial!",
            )
        }

        Column(modifier = Modifier
            .verticalScroll(rememberScrollState())
        ) {

            Column(
                modifier = Modifier.padding(16.dp).fillMaxWidth()
            ) {
                Text(
                    text = "Nama",
                    Modifier.padding(bottom = 8.dp)
                )

                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next
                    )
                )
            }

            Column(
                modifier = Modifier.padding(16.dp).fillMaxWidth()
            ) {
                Text(
                    text = "Email",
                    Modifier.padding(bottom = 8.dp)
                )

                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next
                    )
                )
            }

            Column(
                modifier = Modifier.padding(16.dp).fillMaxWidth()
            ) {
                Text(
                    text = "Nomer Telephone",
                    Modifier.padding(bottom = 8.dp)
                )

                OutlinedTextField(
                    value = phoneNumber,
                    onValueChange = { phoneNumber = it },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next
                    )
                )
            }

            Column(
                modifier = Modifier.padding(16.dp).fillMaxWidth()
            ) {
                Text(
                    text = "Password",
                    Modifier.padding(bottom = 8.dp)
                )

                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    modifier = Modifier.fillMaxWidth(),
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next
                    )
                )
            }

            Column(
                modifier = Modifier.padding(16.dp).fillMaxWidth()
            ) {
                Text(
                    text = "Password",
                    Modifier.padding(bottom = 8.dp)
                )

                OutlinedTextField(
                    value = passwordVerify,
                    onValueChange = { passwordVerify = it },
                    modifier = Modifier.fillMaxWidth(),
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done
                    )
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 40.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(
                    onClick = {/*TODO: Handle Register */},
                    colors = ButtonDefaults.buttonColors(containerColor = if(isFormValid) Primary600 else Color(0xFF98A2B3)),
                    modifier = Modifier
                        .size(width = 330.dp, 50.dp)
                        .height(48.dp)
                        .padding(bottom = 8.dp) ,
                    shape = RoundedCornerShape(6.dp)
                ) {
                    Text(
                        text = "Buat akun",
                        color = Color.White,
                        fontSize = 16.sp
                    )
                }

                OutlinedButton(
                    border = BorderStroke(width = 1.dp, color = Primary600),
                    onClick = { navController.navigate("login_screen") },
                    modifier = Modifier
                        .size(width = 330.dp, 50.dp)
                        .height(48.dp)
                        .padding(bottom = 8.dp),
                    shape = RoundedCornerShape(6.dp),
                    colors = ButtonDefaults.outlinedButtonColors(),
                ) {
                    Text(
                        text = "Sudah punya akun? Masuk",
                        color = Primary600,
                        fontSize = 16.sp
                    )
                }
            }
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//private fun PreviewRegisterScreen() {
//    BatikanTheme {
//        RegisterScreen()
//    }
//}