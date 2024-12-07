package com.example.batikan.data.repositories

import android.util.Log
import com.example.batikan.data.datasource.remote.BatikApiService
import com.example.batikan.data.model.batik_product.BatikDetailsResponse
import com.example.batikan.data.model.batik_product.BatikList
import com.example.batikan.data.model.batik_product.BatikResponse
import com.example.batikan.data.model.batik_scan.BatikScanData
import com.example.batikan.data.model.batik_scan.BatikScanResponse
import okhttp3.Callback
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import javax.inject.Inject

class BatikRepositoryImpl @Inject constructor(
    private val apiService: BatikApiService
) {
    suspend fun getBatik(): List<BatikList> {
        val response = apiService.getBatikList() // Tidak perlu lagi menyisipkan token manual
        if (response.isSuccessful) {
            return response.body()?.data ?: emptyList()
        } else {
            throw Exception("Error: ${response.message()}") // Tangani error
        }
    }

    suspend fun getBatikDetail(batikId: String): Result<BatikDetailsResponse> {
        return try {
            val response = apiService.getBatikDetail(id = batikId)

            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    Result.success(body)
                } else {
                    val errorMessage = "Response body is null"
                    Log.e("BatikRepository", errorMessage)
                    Result.failure(Exception(errorMessage))
                }
            } else {
                val errorBody = response.errorBody()?.string()
                val errorMessage = "Error: ${response.code()} - ${response.message()} | $errorBody"
                Log.e("BatikRepository", errorMessage)
                Result.failure(Exception(errorMessage))
            }
        } catch (e: Exception) {
            Log.e("BatikRepository", "Exception: ${e.message}", e)
            Result.failure(e)
        }
    }

    suspend fun scanBatik(imageFile: File): Result<BatikScanResponse> {
        return try {

            // Buat request body dari file
            val requestFile = imageFile.asRequestBody("image/*".toMediaTypeOrNull())
            Log.d("BatikRepositoryImpl", "Isi request file: ${requestFile.contentType()}")
            val body = MultipartBody.Part.createFormData("image", imageFile.name, requestFile)
            Log.d("BatikRepositoryImpl", "Isi headers: ${body.headers}")
            Log.d("BatikRepositoryImpl", "Isi body: ${body.body}")
            val response = apiService.scanBatik(body)
            Log.d("BatikRepositoryImpl", "Isi response: $response")

            Log.d("BatikRepositoryImpl", "File path: ${imageFile.absolutePath}")
            Log.d("BatikRepositoryImpl", "File exists: ${imageFile.exists()}")
            Log.d("BatikRepositoryImpl", "File size: ${imageFile.length()}")

            if (response.isSuccessful) {
                Result.success(response.body()!!)
            } else {
                val errorBody = response.errorBody()?.string()
                Log.e("BatikRepositoryImpl", "Error: $errorBody")
                Result.failure(Exception(errorBody))
            }
        } catch (e: Exception) {
            Log.e("BatikRepositoryImpl", "Exception: ${e.message}", e)
            Result.failure(e)
        }
    }

    // Untuk membuat image
    private fun createMultipartBody(imageFile: File): MultipartBody.Part {
        val requestFile = imageFile.asRequestBody("image/jpeg".toMediaTypeOrNull())
        return MultipartBody.Part.createFormData("image", imageFile.name, requestFile)
    }
}

