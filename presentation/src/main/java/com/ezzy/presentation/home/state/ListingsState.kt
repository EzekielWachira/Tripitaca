package com.ezzy.presentation.home.state

import com.ezzy.data.domain.model.Property
import com.ezzy.data.utils.StateWrapper

data class ListingsState(
    var state: StateWrapper<List<Property>>
)
