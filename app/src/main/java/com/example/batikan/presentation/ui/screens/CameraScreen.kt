package com.example.batikan.presentation.ui.screens

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import java.net.URLEncoder
import java.text.SimpleDateFormat
import java.util.*
import java.io.File
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun CameraScreen(navController: NavController) {
    val context = LocalContext.current
    var hasCameraPermission by remember { mutableStateOf(false) }
    val cameraProviderFuture = remember { ProcessCameraProvider.getInstance(context) }
    var imageCapture by remember { mutableStateOf<ImageCapture?>(null) }
    var isCameraActive by remember { mutableStateOf(true) }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { granted ->
            hasCameraPermission = granted
        }
    )

    LaunchedEffect(Unit) {
        if (ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            launcher.launch(Manifest.permission.CAMERA)
        } else {
            hasCameraPermission = true
        }
    }

    if (hasCameraPermission) {
        Box(modifier = Modifier.fillMaxSize()) {
            if (isCameraActive) {
                AndroidView(
                    modifier = Modifier.fillMaxSize(),
                    factory = { ctx ->
                        val previewView = PreviewView(ctx)
                        val cameraProvider = cameraProviderFuture.get()

                        cameraProvider.unbindAll()

                        val preview = Preview.Builder().build()
                        val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

                        val imageCaptureInstance = ImageCapture.Builder().build()
                        imageCapture = imageCaptureInstance

                        preview.surfaceProvider = previewView.surfaceProvider
                        cameraProvider.bindToLifecycle(
                            context as androidx.lifecycle.LifecycleOwner,
                            cameraSelector,
                            preview,
                            imageCaptureInstance
                        )
                        previewView
                    }
                )
            }

            // Tombol Capture (Bulatan Putih)
            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 32.dp)
                    .size(70.dp) // Ukuran tombol capture
                    .background(Color.White, CircleShape) // Warna dan bentuk bulat
                    .border(2.dp, Color.Gray, CircleShape) // Tambahkan border
            ) {
                Box(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .size(50.dp)
                        .background(Color.White, CircleShape)
                        .clickable {
                            // Logika pengambilan gambar
                            val fileName = "Batik_${SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(Date())}.jpg"
                            val photoFile = File(context.cacheDir, fileName)

                            val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()

                            imageCapture?.takePicture(
                                outputOptions,
                                ContextCompat.getMainExecutor(context),
                                object : ImageCapture.OnImageSavedCallback {
                                    override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                                        val savedUri = Uri.fromFile(photoFile)
                                        val encodedUri = URLEncoder.encode(savedUri.toString(), "UTF-8")
                                        isCameraActive = false
                                        val cameraProvider = cameraProviderFuture.get()
                                        cameraProvider.unbindAll()
                                        navController.navigate("photo_result_screen?photoUri=${encodedUri}")
                                    }

                                    override fun onError(exception: ImageCaptureException) {
                                        exception.printStackTrace()
                                    }
                                }
                            )
                        }
                )
            }

            // Tombol Kembali (Panah Putih)
            IconButton(
                onClick = {
                    isCameraActive = false
                    val cameraProvider = cameraProviderFuture.get()
                    cameraProvider.unbindAll()
                    navController.navigateUp()
                },
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(16.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.White // Warna panah putih
                )
            }
        }
    } else {
        Box(modifier = Modifier.fillMaxSize()) {
            Text(
                text = "Izinkan akses kamera untuk menggunakan fitur ini.",
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}


