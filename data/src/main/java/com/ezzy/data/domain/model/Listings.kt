package com.ezzy.data.domain.model

import androidx.annotation.Keep

data class Listings(
    val results: List<Property>,
    val total_count: Int
)