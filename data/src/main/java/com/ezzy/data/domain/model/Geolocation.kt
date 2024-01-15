package com.ezzy.data.domain.model

import androidx.annotation.Keep

@Keep
data class Geolocation(
    val lat: Double,
    val lon: Double
)