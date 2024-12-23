package com.example.batikan.presentation.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.batikan.R
import com.example.batikan.presentation.ui.theme.DisplayXsBold
import com.example.batikan.presentation.ui.theme.Primary600
import com.example.batikan.presentation.ui.theme.TextSmallRegular
import com.example.batikan.presentation.viewmodel.LoginState
import com.example.batikan.presentation.viewmodel.AuthViewModel

@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: AuthViewModel = hiltViewModel(),
) {

    val loginState by viewModel.loginState.collectAsState()

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    var isFormValid = email.isNotBlank() && password.isNotBlank()

    var isEmailValid by remember { mutableStateOf(true) }


    Column(modifier = Modifier
        .fillMaxSize()
        .background(Color.White)
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
                text = "Masuk akun",
                style = DisplayXsBold
            )

            Text(
                text = "Selamat datang kembali Sahabat Batikan!",
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
                onValueChange = {
                    email = it
                    isEmailValid = email.matches(Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}\$"))
                },
                modifier = Modifier.fillMaxWidth(),
                isError = !isEmailValid,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done
                )
            )
        }
        if (!isEmailValid) {
            Text(
                text = "Format Email tidak valid",
                color = Primary600,
                style = TextSmallRegular,
                modifier = Modifier.padding(start = 16.dp)
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
                    imeAction = ImeAction.Done
                )
            )
        }

        Row(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Lupa password?",
                Modifier.padding(end = 8.dp)
            )

            Text(
                text = "Reset password"
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp, vertical = 20.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = {
                    if (isFormValid) { viewModel.login(email, password) } },
                colors = ButtonDefaults.buttonColors(containerColor = if(isFormValid) Primary600 else Color(0xFF98A2B3)),
                modifier = Modifier.fillMaxWidth().height(48.dp),
                shape = RoundedCornerShape(6.dp),
                enabled = isFormValid
            ) {
                Text(
                    text = "Masuk kembali",
                    color = Color.White,
                    fontSize = 16.sp
                )
            }

            Spacer(Modifier.height(12.dp))

            when (loginState) {
                is LoginState.Loading -> CircularProgressIndicator()
                is LoginState.Success -> {
                    LaunchedEffect(Unit) {
                        // Navigate to home
                        navController.navigate("home_screen") {
                            popUpTo("login_screen") {
                                inclusive = true
                            }
                        }
                    }
                }
                is LoginState.Error -> Text((loginState as LoginState.Error).message, color = Color.Red)
                else -> {}
            }


            OutlinedButton(
                border = BorderStroke(width = 1.dp, color = Primary600),
                onClick = { navController.navigate("register_screen") },
                modifier = Modifier.fillMaxWidth().height(48.dp),
                shape = RoundedCornerShape(6.dp),
                colors = ButtonDefaults.outlinedButtonColors(),
            ) {
                Text(
                    text = "Baru bergabung? Buat akun",
                    color = Primary600,
                    fontSize = 16.sp
                )
            }
        }
    }
}
