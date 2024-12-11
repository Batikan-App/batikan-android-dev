package com.example.batikan.presentation.ui.screens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.batikan.presentation.ui.composables.TextFieldWithTitle
import com.example.batikan.presentation.ui.theme.BatikanTheme
import com.example.batikan.presentation.ui.theme.Primary50
import com.example.batikan.presentation.ui.theme.Primary600
import com.example.batikan.presentation.ui.theme.TextMdSemiBold
import com.example.batikan.presentation.ui.theme.TextPrimary
import com.example.batikan.presentation.ui.theme.TextSmallRegular
import com.example.batikan.presentation.ui.theme.TextSmallSemiBold
import com.example.batikan.presentation.ui.theme.White
import com.example.batikan.presentation.viewmodel.RegisterState
import com.example.batikan.presentation.viewmodel.UpdateState
import com.example.batikan.presentation.viewmodel.UserState
import com.example.batikan.presentation.viewmodel.UserViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateProfileContent(
    navController: NavController,
    modifier: Modifier = Modifier,
    userViewModel: UserViewModel = hiltViewModel()
) {
    var name by remember { mutableStateOf("")}
    var email by remember { mutableStateOf("")}
    var phoneNumber by remember { mutableStateOf("")}
    var password by remember { mutableStateOf("")}
    var verifyPassword by remember { mutableStateOf("")}

    val profileState by userViewModel.userState.collectAsState()
    val updateState by userViewModel.updateState.collectAsState()

//    var isPasswordValid by remember { mutableStateOf(true) }
//    var isVerifyPasswordValid by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        userViewModel.fetchUserProfile()
    }

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
            when (updateState) {
                is UpdateState.Loading -> CircularProgressIndicator()
                is UpdateState.Success -> {
                    LaunchedEffect(Unit) {
                        // Navigate to profile
                        navController.navigate("profile_screen") {
                            popUpTo("update_profile_screen") {
                                inclusive = true
                            }
                        }
                    }
                }
                is UpdateState.Error -> Text((updateState as RegisterState.Error).message, color = Color.Red)
                else -> {}
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Primary50)
                    .padding(horizontal = 30.dp)
                    .padding(vertical = 20.dp),
                horizontalArrangement = Arrangement.SpaceBetween,

                ) {
                Button(
                    onClick = { userViewModel.updateProfile(name, email, phoneNumber) },
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
        when (profileState) {
            is UserState.Loading -> {
                CircularProgressIndicator()

            }
            is UserState.Success -> {
                val user = (profileState as UserState.Success).data
                name = user.name
                email = user.email
                phoneNumber = user.phone
            }
            is UserState.Error -> {
                Text(
                    text = (profileState as UserState.Error).message,
                    color = Color.Red,
                    style = TextMdSemiBold
                )
            }
            else -> {
                Text(
                    text = "Data profil belum dimuat",
                    style = TextMdSemiBold
                )
            }
        }
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
//
//            item {
//                Column(
//                    modifier = Modifier.padding(16.dp).fillMaxWidth()
//                ) {
//                    Text(
//                        text = "Password",
//                        Modifier.padding(bottom = 8.dp)
//                    )
//
//                    OutlinedTextField(
//                        value = password,
//                        onValueChange = {
//                            password = it
//                            isPasswordValid = password.matches(
//                                Regex("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@\$!%*#?&])[A-Za-z\\d@\$!%*#?&]{8,}\$")
//                            )
//                        },
//                        modifier = Modifier.fillMaxWidth(),
//                        isError = !isPasswordValid,
//                        visualTransformation = PasswordVisualTransformation(),
//                        keyboardOptions = KeyboardOptions(
//                            imeAction = ImeAction.Next
//                        )
//                    )
//                }
//
//                if (!isPasswordValid) {
//                    Text(
//                        text = "Password harus berisi huruf, angka, dan simbol",
//                        color = Primary600,
//                        style = TextSmallRegular,
//                        modifier = Modifier.padding(start = 16.dp)
//                    )
//                }
//            }
//
//            item {
//                Column(
//                    modifier = Modifier.padding(16.dp).fillMaxWidth()
//                ) {
//                    Text(
//                        text = "Password",
//                        Modifier.padding(bottom = 8.dp)
//                    )
//
//                    OutlinedTextField(
//                        value = verifyPassword,
//                        onValueChange = {
//                            verifyPassword = it
//                            isVerifyPasswordValid = password == verifyPassword
//                        },
//                        modifier = Modifier.fillMaxWidth(),
//                        isError = !isVerifyPasswordValid,
//                        visualTransformation = PasswordVisualTransformation(),
//                        keyboardOptions = KeyboardOptions(
//                            imeAction = ImeAction.Done
//                        )
//                    )
//                }
//
//                if (!isVerifyPasswordValid) {
//                    Text(
//                        text = "Password tidak sama",
//                        color = Primary600,
//                        style = TextSmallRegular,
//                        modifier = Modifier.padding(start = 16.dp)
//                    )
//                }
//
//            }
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