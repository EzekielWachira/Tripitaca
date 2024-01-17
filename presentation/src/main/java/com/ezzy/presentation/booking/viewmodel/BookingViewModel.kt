package com.ezzy.presentation.booking.viewmodel

import androidx.lifecycle.ViewModel
import com.ezzy.presentation.booking.state.GuestState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class BookingViewModel @Inject constructor() : ViewModel() {

    private val _guestState = MutableStateFlow(GuestState())
    val guestState get() = _guestState.asStateFlow()

    fun setGuest(numberOfGuest: Int, guestType: GuestType) {
        when (guestType) {
            GuestType.ADULT -> {
                _guestState.update {
                    guestState.value.copy(
                        adults = numberOfGuest
                    )
                }
            }

            GuestType.CHILDREN -> {
                _guestState.update {
                    guestState.value.copy(
                        children = numberOfGuest
                    )
                }
            }

            GuestType.INFANT -> {
                _guestState.update {
                    guestState.value.copy(
                        adults = numberOfGuest
                    )
                }
            }
        }
    }

}


enum class GuestType {
    ADULT,
    CHILDREN,
    INFANT
}