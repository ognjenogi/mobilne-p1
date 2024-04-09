package com.example.projekat1.repository

import android.util.Log
import com.example.projekat1.breeds.Breed
import com.example.projekat1.breeds.BreedDetails
import com.example.projekat1.breeds.CatImage
import com.example.projekat1.config.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.URLEncoder

object BreedRepository {
    private val catApiService = RetrofitClient.create()

    suspend fun getBreeds(): List<Breed>? {
        return try {
            val response = catApiService.getBreeds()
            if (response.isSuccessful) {
                response.body()
            } else {
                Log.e("BreedsListActivity", "Unsuccessful response: ${response.code()}")
                null
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e("BreedsListActivity", "Exception while fetching breeds: ${e.message}")
            null
        }
    }

    suspend fun searchBreedsByName(name: String): List<Breed>? {

        return try {
            val response = catApiService.getBreedsByQuery(name)
            Log.d("pretraga", response.body().toString())
            if (response.isSuccessful) {
                response.body()
            } else {
                null
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e("BreedsListActivity", "Exception while fetching breeds: ${e.message}")
            null
        }
    }
    suspend fun findById(id: String): BreedDetails? {
        return try {
            val response = catApiService.getBreedDetails(id)
            if (response.isSuccessful) {
                response.body()
            } else {
                null
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e("BreedsListActivity", "Exception while fetching breeds: ${e.message}")
            null
        }
    }
suspend fun findPictureById(id: String): List<CatImage>? {
    return try {
        val response = catApiService.getImagesByBreedId(id)
        if (response.isSuccessful) {
            response.body()
        } else {
            null
        }
    } catch (e: Exception) {
        e.printStackTrace()
        Log.e("BreedsListActivity", "Exception while fetching breeds: ${e.message}")
        null
    }
}
}