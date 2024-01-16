package com.ezzy.presentation.listing_detail.state

import com.ezzy.data.domain.model.Property
import com.ezzy.data.utils.StateWrapper

data class SingleListingState(
    var state: StateWrapper<Property?>
)
