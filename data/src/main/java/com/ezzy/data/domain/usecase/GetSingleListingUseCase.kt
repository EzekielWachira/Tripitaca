package com.ezzy.data.domain.usecase

import android.util.Log
import com.ezzy.data.domain.repository.ListingsRepository
import com.ezzy.data.utils.StateWrapper
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

private const val TAG = "GetSingleListingUseCase"
class GetSingleListingUseCase @Inject constructor(
    private val listingsRepository: ListingsRepository
) {
    suspend operator fun invoke(id: String) = flow {
        try {
            val listing = listingsRepository.getListing(id)
            Log.d(TAG, "invoke: $listing")
            emit(StateWrapper.Success(listing))
        } catch (th: Throwable) {
            emit(StateWrapper.Failure(th.message ?: "An Error Occurred"))
        }
    }.onStart {
        emit(StateWrapper.Loading)
    }.catch {
        emit(StateWrapper.Failure(it.message ?: "An Error Occurred"))
    }

}