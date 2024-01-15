package com.ezzy.data.domain.model

data class Filter(
    val title: String,
    var isSelected: Boolean = false
)


val mainFilters = listOf(
    Filter(title = "TV"),
    Filter(title = "Internet"),
    Filter(title = "Air Conditioning"),
    Filter(title = "Kitchen"),
    Filter(title = "Heating"),
    Filter(title = "Buzzer/Wireless Intercom"),
    Filter(title = "Hangers"),
    Filter(title = "Hair Driers"),
    Filter(title = "Iron"),
    Filter(title = "Washer"),
)
