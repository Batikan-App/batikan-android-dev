package com.example.batikan.data.repositories

import android.util.Log
import com.example.batikan.data.datasource.remote.BatikApiService
import com.example.batikan.data.model.batik_details.BatikDetailsResponse
import com.example.batikan.data.model.batik_origin.BatikOriginDetails
import com.example.batikan.data.model.batik_product.BatikDetails
import com.example.batikan.data.model.batik_product.BatikList
import com.example.batikan.data.model.batik_scan.BatikScanResponse
import com.example.batikan.data.model.batik_search.BatikSearchDetails
import com.example.batikan.data.model.batik_search.BatikSearchResponse
import com.example.batikan.domain.repositories.BatikRepository
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import javax.inject.Inject

class BatikRepositoryImpl @Inject constructor(
    private val apiService: BatikApiService
): BatikRepository {
    override suspend fun getBatik(): List<BatikList> {
        val response = apiService.getBatikList() // Tidak perlu lagi menyisipkan token manual
        if (response.isSuccessful) {
            return response.body()?.data ?: emptyList()
        } else {
            throw Exception("Error: ${response.message()}") // Tangani error
        }
    }

     override suspend fun getBatikOrigin(origin: String): List<BatikOriginDetails> {
        val response = apiService.getBatikOrigin(origin)
        if (response.isSuccessful) {
            return response.body()?.data ?: emptyList()
        } else {
            throw Exception("Error: ${response.message()}")
        }
    }

    override suspend fun getBatikDetail(batikId: String): Result<BatikDetailsResponse> {
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

    override suspend fun scanBatik(imageFile: File): Result<BatikScanResponse> {
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

    override suspend fun searchBatik(query: String): List<BatikSearchDetails> {
        val response = apiService.searchBatik(query)
        if (response.isSuccessful) {
            return response.body()?.data ?: emptyList()
        } else {
            Log.d("BatikSearch", "Error searching: ${response.message()}")
            throw Exception("Error: ${response.message()}")
        }
    }

    // Untuk membuat image
    private fun createMultipartBody(imageFile: File): MultipartBody.Part {
        val requestFile = imageFile.asRequestBody("image/jpeg".toMediaTypeOrNull())
        return MultipartBody.Part.createFormData("image", imageFile.name, requestFile)
    }
}

