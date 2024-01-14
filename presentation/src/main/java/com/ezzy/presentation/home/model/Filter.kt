package com.ezzy.presentation.home.model

data class Filter(
    val title: String,
    var isSelected: Boolean = false
)


val filters = listOf(
    Filter("Kitchen", true),
    Filter("Wifi", false),
    Filter("Heating", false),
    Filter("TV", true),
    Filter("Iron", false),
)