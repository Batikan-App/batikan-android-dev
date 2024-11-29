//package com.example.batikan.data.remote
//
//import androidx.datastore.core.DataStore
//import androidx.datastore.preferences.core.Preferences
//import androidx.datastore.preferences.core.stringPreferencesKey
//import com.example.batikan.data.remote.Batik
//import com.example.batikan.data.remote.BatikApiService
//import kotlinx.coroutines.flow.Flow
//import kotlinx.coroutines.flow.first
//import kotlinx.coroutines.flow.map
//import retrofit2.Retrofit
//import retrofit2.converter.gson.GsonConverterFactory
//import javax.inject.Inject
//
//class BatikRepositoryImpl @Inject constructor(
//    private val batikApiService: BatikApiService,
//    private val datastore: DataStore<Preferences>
//) {
//    suspend fun fetchBatikList(): List<Batik> {
//        val token = datastore.data.map { preferences ->
//            preferences[stringPreferencesKey("auth_token")] ?: ""
//        }.first()
//
//        if (token.isEmpty()) throw Exception("Token not found")
//
//        val response = batikApiService.getBatikList(token)
//        if (response.status != "success") throw Exception("Failed to fetch data")
//        return response.data
//    }
//}