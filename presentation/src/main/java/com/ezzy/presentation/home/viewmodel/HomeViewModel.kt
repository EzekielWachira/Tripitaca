package com.ezzy.presentation.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ezzy.data.domain.model.Filter
import com.ezzy.data.domain.model.Property
import com.ezzy.data.domain.model.mainFilters
import com.ezzy.data.domain.repository.PreferenceRepository
import com.ezzy.data.domain.usecase.GetAllListingUseCase
import com.ezzy.data.utils.StateWrapper
import com.ezzy.presentation.home.state.ListingsState
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


private const val TAG = "HomeViewModel"

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getAllListingUseCase: GetAllListingUseCase,
    private val preferenceRepository: PreferenceRepository
) : ViewModel() {

    private var _listings: MutableStateFlow<List<Property>> = MutableStateFlow(emptyList())
    val listings get() = _listings.asStateFlow()

    private val _listingState = MutableStateFlow(ListingsState(StateWrapper.Empty))
    val listingState get() = _listingState.asStateFlow()

    val filters = preferenceRepository.filters
    val user = preferenceRepository.user
    val firebaseUser = MutableStateFlow(FirebaseAuth.getInstance().currentUser)

    init {
        checkFilters()
    }

    private fun checkFilters() {
        viewModelScope.launch {
            if (filters.firstOrNull().isNullOrEmpty()) {
                saveFilters(mainFilters)
            }
        }
    }

    fun getListings() {
        viewModelScope.launch {
            val filters = filters.firstOrNull()
            filters?.let {
                getAllListingUseCase.invoke(it.filter { fl -> fl.isSelected }).collectLatest { state ->
                    _listingState.update {
                        listingState.value.copy(
                            state = state
                        )
                    }
                }

            }

        }
    }

    private fun saveFilters(amenitiesFilters: List<Filter>) {
        viewModelScope.launch {
            preferenceRepository.saveFilters(amenitiesFilters)
        }
    }

    fun applyFilter(filter: Filter, filters: List<Filter>) {
        viewModelScope.launch {
            preferenceRepository.applyFilter(filter, filters)
            delay(500)
            getListings()
        }
    }

}