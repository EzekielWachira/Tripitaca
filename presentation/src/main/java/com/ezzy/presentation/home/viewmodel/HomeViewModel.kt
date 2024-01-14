package com.ezzy.presentation.home.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ezzy.data.domain.model.Property
import com.ezzy.data.domain.usecase.GetAllListingUseCase
import com.ezzy.data.utils.StateWrapper
import com.ezzy.presentation.home.state.ListingsState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


private const val TAG = "HomeViewModel"
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getAllListingUseCase: GetAllListingUseCase
) : ViewModel() {

    private var _listings: MutableStateFlow<List<Property>> = MutableStateFlow(emptyList())
    val listings get() = _listings.asStateFlow()

    private val _listingState = MutableStateFlow(ListingsState(StateWrapper.Empty))
    val listingState get() = _listingState.asStateFlow()


    fun getListings() {
        viewModelScope.launch {
            getAllListingUseCase.invoke().collectLatest { state ->
                _listingState.update {
                    listingState.value.copy(
                        state = state
                    )
                }
            }
        }
    }

}