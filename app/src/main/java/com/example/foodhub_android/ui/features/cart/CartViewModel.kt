package com.example.foodhub_android.ui.features.cart

import androidx.lifecycle.ViewModel
import com.example.foodhub_android.data.models.CartItem
import com.example.foodhub_android.data.models.CartResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(): ViewModel() {
    val _uiState = MutableStateFlow<CartUiState>(CartUiState.Loading)
        
    val uiState = _uiState.asStateFlow()

    val _event = MutableSharedFlow<CartEvent>()
    val event = _event.asSharedFlow()

        fun incrementQuantity(cartItem: CartItem) {
//            if (cartItem.quantity == 5) {
//                return
//            }
//            updateItemQuantity(cartItem, cartItem.quantity + 1)
        }

        fun decrementQuantity(cartItem: CartItem) {
//            if (cartItem.quantity == 1) {
//                return
//            }
//            updateItemQuantity(cartItem, cartItem.quantity - 1)
        }
        fun removeItem(cartItem: CartItem) {
//            viewModelScope.launch {
//                _uiState.value = CartUiState.Loading
//                val res =
//                    safeApiCall { foodApi.deleteCartItem(cartItem.id) }
//                when (res) {
//                    is ApiResponse.Success -> {
//                        getCart()
//                    }
//
//                    else -> {
//                        cartResponse?.let {
//                            _uiState.value = CartUiState.Success(cartResponse!!)
//                        }
//                        errorTitle = "Cannot Delete"
//                        errorMessage = "An error occurred while deleting the item"
//                        _event.emit(CartEvent.onItemRemoveError)
//                    }
//                }
//            }
        }

        fun checkout() {
//            viewModelScope.launch {
//                _uiState.value = CartUiState.Loading
//                val paymentDetails =
//                    safeApiCall { foodApi.getPaymentIntent(PaymentIntentRequest(address.value!!.id!!)) }
//
//                when (paymentDetails) {
//                    is ApiResponse.Success -> {
//                        paymentIntent = paymentDetails.data
//                        _event.emit(CartEvent.OnInitiatePayment(paymentDetails.data))
//                        _uiState.value = CartUiState.Success(cartResponse!!)
//                    }
//
//                    else -> {
//                        errorTitle = "Cannot Checkout"
//                        errorMessage = "An error occurred while checking out"
//                        _event.emit(CartEvent.showErrorDialog)
//                        _uiState.value = CartUiState.Success(cartResponse!!)
//                    }
//                }
//
//            }
        }

    sealed class CartUiState(){
        object Nothing : CartUiState()
        object Loading : CartUiState()
        data class Error(val message : String) : CartUiState()
        data class Success(val data : CartResponse) : CartUiState()
    }

    sealed class CartEvent{
        object showErrorDialog : CartEvent()
        object onCheckout : CartEvent()
    }
}