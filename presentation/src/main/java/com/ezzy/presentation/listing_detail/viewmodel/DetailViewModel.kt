package com.ezzy.presentation.listing_detail.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ezzy.data.domain.model.Property
import com.ezzy.data.domain.usecase.GetSingleListingUseCase
import com.ezzy.data.utils.StateWrapper
import com.ezzy.presentation.listing_detail.state.SingleListingState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


private const val TAG = "DetailViewModel"
@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getSingleListingUseCase: GetSingleListingUseCase
): ViewModel() {

    private val _singleListingState = MutableStateFlow(SingleListingState(StateWrapper.Empty))
    val singleListingState get() = _singleListingState.asStateFlow()

    private var _property = MutableStateFlow<Property?>(null)
    val property get() = _property.asStateFlow()


    fun setProperty(property: Property){
        _property.value = property
    }

    fun getListing(id: String) {

        viewModelScope.launch {
            getSingleListingUseCase(id).collectLatest { state->
                _singleListingState.update {
                    singleListingState.value.copy(
                        state = state
                    )
                }
            }
        }
    }

}