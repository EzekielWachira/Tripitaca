package com.ezzy.data.data

import android.content.Context
import android.util.Log
import com.ezzy.data.domain.model.Listings
import com.ezzy.data.domain.model.Property
import com.ezzy.data.domain.repository.ListingsRepository
import com.ezzy.data.utils.property
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.json.JSONArray
import org.json.JSONObject
import java.io.File

private const val TAG = "ListingRepositoryImpl"

class ListingRepositoryImpl(private val context: Context): ListingsRepository {

    private lateinit var jsonString: String

    override suspend fun getAllListings(): List<Property> {
       try {
            jsonString = context.assets.open("listings.json")
                .bufferedReader()
                .use { it.readText() }

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

    override suspend fun getListing(listingId: String): Property? {

        jsonString = context.assets.open("listings.json")
            .bufferedReader()
            .use { it.readText() }

        val jsonObject = JSONObject(jsonString)
        val item = jsonObject.getJSONArray("results")
        val listItem = findObjectById(listingId, item)




        Log.e(TAG, "getListing: $listingId" )
        val listings = getAllListings()
        val listing = listings.firstOrNull {
            it.id == listingId
        }
        Log.d(TAG, "getListing: $listing")

//        if (!::jsonString.isInitialized || jsonString.isBlank()) {
//            jsonString = context.assets.open("listings.json")
//                .bufferedReader()
//                .use { it.readText() }
//        }

//        val  listings = Gson().fromJson(jsonString, Listings::class.java).results

        return listings.find { property ->
            property.id == listingId
        }
    }

    fun findObjectById(id: String, jsonArray: JSONArray): JSONObject? {
        for (index in 0 until jsonArray.length()) {
            val jsonObject = jsonArray.getJSONObject(index)
            if (jsonObject.getString("id") == id) {
                return jsonObject
            }
        }
        return null
    }
}