package com.codewithfk.foodhub.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodhub_android.data.FoodApi
import com.example.foodhub_android.data.models.Delieveries
import com.example.foodhub_android.data.models.delieveriesListResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DeliveriesViewModel @Inject constructor(val foodApi: FoodApi): ViewModel() {

    val _deliveriesState = MutableStateFlow<DeliveriesState>(DeliveriesState.Loading)
    val deliveriesState = _deliveriesState.asStateFlow()

    val _deliveriesEvent = MutableSharedFlow<DeliveriesEvent?>()
    val deliveriesEvent = _deliveriesEvent.asSharedFlow()


    init {
        getDeliveries()
    }

    fun getDeliveries() {
        viewModelScope.launch {
            try {
//                _deliveriesState.value = DeliveriesState.Loading
//                val response = safeApiCall { foodApi.getAvailableDeliveries() }
//                when (response) {
//                    is ApiResponse.Success -> {
//                        _deliveriesState.value = DeliveriesState.Success(response.data.data)
//                        deliveries.value = response.data
//                    }
//
//                    is ApiResponse.Error -> {
//                        _deliveriesState.value = DeliveriesState.Error(response.message)
//                    }
//
//                    else -> {
//                        _deliveriesState.value = DeliveriesState.Error("An error occurred")
//                    }
                _deliveriesState.value = DeliveriesState.Success(delieveriesListResponse.data)
            } catch (e: Exception) {
                _deliveriesState.value = DeliveriesState.Error("An error occurred")
            }
        }

    }


    sealed class DeliveriesState{
        object Loading: DeliveriesState()
        data class Success(val deliveries: List<Delieveries>): DeliveriesState()
        data class Error(val message: String): DeliveriesState()
    }
    sealed class DeliveriesEvent{
        object NavigateToOrderDetails : DeliveriesEvent()
        data class ShowError(val message: String) : DeliveriesEvent()
    }
}