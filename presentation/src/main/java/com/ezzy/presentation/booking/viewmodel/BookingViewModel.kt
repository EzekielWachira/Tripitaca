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

    fun setGuest(numberOfGuest: Int, guestType: GuestType, state: GuestState, event: Event) {
        when (guestType) {
            GuestType.ADULT -> {
                _guestState.update {
                    guestState.value.copy(
                        adults = when(event) {
                            Event.ADD -> state.adults + numberOfGuest
                            Event.SUBTRACT -> state.adults - numberOfGuest
                        }
                    )
                }
            }

            GuestType.CHILDREN -> {
                _guestState.update {
                    guestState.value.copy(
                        children = when(event) {
                            Event.ADD -> state.children + numberOfGuest
                            Event.SUBTRACT -> state.children - numberOfGuest
                        }
                    )
                }
            }

            GuestType.INFANT -> {
                _guestState.update {
                    guestState.value.copy(
                        infant = when(event) {
                            Event.ADD -> state.infant + numberOfGuest
                            Event.SUBTRACT -> state.infant - numberOfGuest
                        }
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

enum class Event {
    ADD,
    SUBTRACT
}