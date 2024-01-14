package com.ezzy.data.domain.repository

import com.ezzy.data.domain.model.Property

interface ListingsRepository {

    suspend fun getAllListings(): List<Property>

    suspend fun searchListing(query: String): List<Property>

    suspend fun filterListing(filters: List<String>): List<Property>

}