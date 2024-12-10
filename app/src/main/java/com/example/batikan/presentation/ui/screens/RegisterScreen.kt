package com.example.batikan.presentation.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.batikan.R
import com.example.batikan.presentation.ui.theme.BatikanTheme
import com.example.batikan.presentation.ui.theme.DisplayXsBold
import com.example.batikan.presentation.ui.theme.Primary600
import com.example.batikan.presentation.ui.theme.TextSmallRegular
import com.example.batikan.presentation.viewmodel.AuthViewModel
import com.example.batikan.presentation.viewmodel.LoginState
import com.example.batikan.presentation.viewmodel.RegisterState

@Composable
fun RegisterScreen(
    navController: NavController,
    viewModel: AuthViewModel = hiltViewModel()
) {
    var isButtonClicked by remember { mutableStateOf(false) }

    val registerState by viewModel.registerState.collectAsState()

    var name by remember { mutableStateOf("")}
    var email by remember { mutableStateOf("")}
    var phoneNumber by remember { mutableStateOf("")}
    var password by remember { mutableStateOf("")}
    var passwordVerify by remember { mutableStateOf("")}

    var isFormValid = name.isNotBlank() && email.isNotBlank() && phoneNumber.isNotBlank() && password.isNotBlank() && passwordVerify.isNotBlank()

    var isEmailValid by remember { mutableStateOf(true) }
    var isPhoneValid by remember { mutableStateOf(true) }
    var isPasswordValid by remember { mutableStateOf(true) }
    var isVerifyPasswordValid by remember { mutableStateOf(true) }


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
                    onValueChange = {
                        email = it
                        isEmailValid = email.matches(Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}\$"))
                    },
                    modifier = Modifier.fillMaxWidth(),
                    isError = !isEmailValid,
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next
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
                    text = "Nomer Telephone",
                    Modifier.padding(bottom = 8.dp)
                )

                OutlinedTextField(
                    value = phoneNumber,
                    onValueChange = {
                        phoneNumber = it
                        isPhoneValid = phoneNumber.matches(Regex("^\\d{10,15}\$"))
                    },
                    modifier = Modifier.fillMaxWidth(),
                    isError = !isPhoneValid,
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next
                    )
                )
            }

            if (!isPhoneValid) {
                Text(
                    text = "Nomor Telepohone harus 10 - 15 digit",
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
                    onValueChange = {
                        password = it
                        isPasswordValid = password.matches(
                            Regex("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@\$!%*#?&])[A-Za-z\\d@\$!%*#?&]{8,}\$")
                        )
                                    },
                    modifier = Modifier.fillMaxWidth(),
                    isError = !isPasswordValid,
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next
                    )
                )
            }

            if (!isPasswordValid) {
                Text(
                    text = "Password harus berisi huruf, angka, dan simbol",
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
                    value = passwordVerify,
                    onValueChange = {
                        passwordVerify = it
                        isVerifyPasswordValid = passwordVerify == password
                    },
                    modifier = Modifier.fillMaxWidth(),
                    isError = !isVerifyPasswordValid,
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done
                    )
                )
            }

            if (!isVerifyPasswordValid) {
                Text(
                    text = "Password tidak sama",
                    color = Primary600,
                    style = TextSmallRegular,
                    modifier = Modifier.padding(start = 16.dp)
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 20.dp, vertical = 30.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(
                    onClick = {
                        if (isFormValid) {
                            viewModel.register(name, email, phoneNumber, password, passwordVerify)
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = if(isFormValid) Primary600 else Color(0xFF98A2B3)),
                    modifier = Modifier.fillMaxWidth().height(48.dp),
                    shape = RoundedCornerShape(6.dp),
                    enabled = isFormValid
                ) {
                    Text(
                        text = "Buat akun",
                        color = Color.White,
                        fontSize = 16.sp
                    )
                }

                Spacer(Modifier.height(12.dp))

                when (registerState) {
                    is RegisterState.Loading -> CircularProgressIndicator()
                    is RegisterState.Success -> {
                        LaunchedEffect(Unit) {
                            // Navigate to home
                            navController.navigate("login_screen") {
                                popUpTo("register_screen") {
                                    inclusive = true
                                }
                            }
                        }
                    }
                    is RegisterState.Error -> Text((registerState as RegisterState.Error).message, color = Color.Red)
                    else -> {}
                }

                OutlinedButton(
                    border = BorderStroke(width = 1.dp, color = Primary600),
                    onClick = { navController.navigate("login_screen") },
                    modifier = Modifier.fillMaxWidth().height(48.dp),

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
