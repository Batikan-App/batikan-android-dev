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

@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun CameraScreen(navController: NavController) {
    val context = LocalContext.current
    var hasCameraPermission by remember { mutableStateOf(false) }
    val cameraProviderFuture = remember { ProcessCameraProvider.getInstance(context) }
    var imageCapture by remember { mutableStateOf<ImageCapture?>(null) }

    // Request Camera Permission
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
                // TODO: Rebuild surfaceProvider to have null values if user click back button to take picture again
            )

            // Tombol ambil foto
            Button(
                onClick = {
                    val contentValues = ContentValues().apply {
                        put(MediaStore.MediaColumns.DISPLAY_NAME, "Batik_${SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(Date())}")
                        put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
                        put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES + "/Batikan")
                    }

                    // Buat URI untuk menyimpan foto
//                    val photoUri = context.contentResolver.insert(
//                        MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY),
//                        contentValues
//                    )

                    val photoUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI

                    Log.d("CameraScreen", "PhotoUri: $photoUri")

                    if (photoUri != null) {
                        Log.d("CameraScreen", "PhotoUri: $photoUri")
                        val outputOptions = ImageCapture.OutputFileOptions.Builder(
                            context.contentResolver,
                            photoUri,
                            contentValues
                        ).build()

                        imageCapture?.takePicture(
                            outputOptions,
                            ContextCompat.getMainExecutor(context),
                            object : ImageCapture.OnImageSavedCallback {
                                override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                                    val savedUri = outputFileResults.savedUri ?: photoUri
                                    val encodedUri = URLEncoder.encode(savedUri.toString(), "UTF-8")
                                    Toast.makeText(context, "Foto disimpan", Toast.LENGTH_SHORT).show()
                                    Log.d("CameraScreen", "Image saved successfully")
                                    navController.navigate("photo_result_screen?photoUri=${encodedUri}")
                                    Log.d("CameraScreen", "Navigating to photo_result_screen with photoUri=$encodedUri")
                                }

                                override fun onError(exception: ImageCaptureException) {
                                    exception.printStackTrace()
                                    Toast.makeText(context, "Foto gagal disimpan", Toast.LENGTH_SHORT).show()
                                    Log.e("CameraScreen", "Image capture failed: ${exception.message}", exception)
                                }
                            }
                        )
                    } else {
                        Log.e("CameraScreen", "Failed to create URI for saving photo.")
                        println("Gagal membuat URI untuk menyimpan foto.")
                    }
                },
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(16.dp)
            ) {
                Text("Ambil Foto")
            }

            // Tombol kembali
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
        // Pesan jika izin kamera belum diberikan
        Box(modifier = Modifier.fillMaxSize()) {
            Text(
                text = "Izinkan akses kamera untuk menggunakan fitur ini.",
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}