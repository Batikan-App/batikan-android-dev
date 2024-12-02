package com.example.batikan.presentation.ui.screens

import android.Manifest
import android.content.ContentValues
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import java.net.URLEncoder
import java.text.SimpleDateFormat
import java.util.*

import java.io.File

@Composable
fun CameraScreen(navController: NavController) {
    val context = LocalContext.current
    var hasCameraPermission by remember { mutableStateOf(false) }
    val cameraProviderFuture = remember { ProcessCameraProvider.getInstance(context) }
    var imageCapture by remember { mutableStateOf<ImageCapture?>(null) }

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

            Button(
                onClick = {
                    // Membuat nama file untuk gambar
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
                                Toast.makeText(context, "Foto disimpan", Toast.LENGTH_SHORT).show()
                                Log.d("CameraScreen", "Image saved successfully")
                                navController.navigate("photo_result_screen?photoUri=${encodedUri}")
                            }

                            override fun onError(exception: ImageCaptureException) {
                                exception.printStackTrace()
                                Toast.makeText(context, "Foto gagal disimpan", Toast.LENGTH_SHORT).show()
                                Log.e("CameraScreen", "Image capture failed: ${exception.message}", exception)
                            }
                        }
                    )
                },
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(16.dp)
            ) {
                Text("Ambil Foto")
            }

            Button(
                onClick = { navController.navigateUp() },
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(16.dp)
            ) {
                Text("Kembali")
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
