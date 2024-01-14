package com.ezzy.data.domain.model

data class Listings(
    val results: List<Property>,
    val total_count: Int
)