package com.ezzy.data.data

import android.content.Context
import android.util.Log
import com.ezzy.data.domain.model.Listings
import com.ezzy.data.domain.model.Property
import com.ezzy.data.domain.repository.ListingsRepository
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File

private const val TAG = "ListingRepositoryImpl"

class ListingRepositoryImpl(private val context: Context): ListingsRepository {

    private lateinit var jsonString: String

    override suspend fun getAllListings(): List<Property> {
       try {
            jsonString = context.assets.open("listings.json")
                .bufferedReader()
                .use { it.readText() }

           Log.d(TAG, "getAllListings: $jsonString")
           return Gson().fromJson(jsonString, Listings::class.java).results
       } catch (e: Exception) {
           e.printStackTrace()
           Log.e(TAG, "getAllListings: ", e)
       }

        return emptyList()
    }

    override suspend fun searchListing(query: String): List<Property> {
        TODO("Not yet implemented")
    }

    override suspend fun filterListing(filters: List<String>): List<Property> {
        TODO("Not yet implemented")
    }
}