package com.codewithfk.foodhub.ui.feature.cart

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodhub_android.data.FoodApi
import com.example.foodhub_android.data.models.CartItem
import com.example.foodhub_android.data.models.CartResponse
import com.example.foodhub_android.data.models.UpdateCartItemRequest
import com.example.foodhub_android.data.remote.ApiResponse
import com.example.foodhub_android.data.remote.safeApiCall
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(val foodApi: FoodApi) : ViewModel() {
    val _uiState = MutableStateFlow<CartUiState>(CartUiState.Loading)

    val uiState = _uiState.asStateFlow()

    val _event = MutableSharedFlow<CartEvent>()
    val event = _event.asSharedFlow()

    private var cartResponse: CartResponse? = null

    private val _cartItemCount = MutableStateFlow(0)

    var errorTitle: String = ""
    var errorMessage: String = ""

    init {
        getCart()
    }

    fun getCart() {
        viewModelScope.launch {
            _uiState.value = CartUiState.Loading
            val res = safeApiCall { foodApi.getCart() }
            when (res) {
                is ApiResponse.Success -> {
                    cartResponse = res.data
                    _cartItemCount.value = res.data.items.size
                    _uiState.value = CartUiState.Success(res.data)
                }

                is ApiResponse.Error -> {
                    _uiState.value = CartUiState.Error(res.message)
                }

                else -> {
                    _uiState.value = CartUiState.Error("An error occurred")
                }
            }
        }
    }

    private fun updateItemQuantity(cartItem: CartItem, quantity: Int) {
        viewModelScope.launch {
            _uiState.value = CartUiState.Loading
            val res =
                safeApiCall {
                    foodApi.updateCart(
                        UpdateCartItemRequest(
                            cartItemId = cartItem.id,
                            quantity = quantity
                        )
                    )
                }
            when (res) {
                is ApiResponse.Success -> {
                    getCart()
                }

                else -> {
                    cartResponse?.let {
                        _uiState.value = CartUiState.Success(cartResponse!!)
                    }
                    errorTitle = "Cannot Update Quantity"
                    errorMessage = "An error occurred while updating the quantity of the item"
                    _event.emit(CartEvent.onQuantityUpdateError)
                }
            }
        }
    }

    fun incrementQuantity(cartItem: CartItem) {
        if (cartItem.quantity == 5) {
            return
        }
        updateItemQuantity(cartItem, cartItem.quantity + 1)
    }

    fun decrementQuantity(cartItem: CartItem) {
        if (cartItem.quantity == 1) {
            return
        }
        updateItemQuantity(cartItem, cartItem.quantity - 1)
    }

    fun removeItem(cartItem: CartItem) {
        Log.d("CartViewModel", "removeItem: $cartItem")
        viewModelScope.launch {
            _uiState.value = CartUiState.Loading
            val res =
                safeApiCall { foodApi.deleteCartItem(cartItem.id) }
            when (res) {
                is ApiResponse.Success -> {
                    getCart()
                }

                else -> {
                    cartResponse?.let {
                        _uiState.value = CartUiState.Success(cartResponse!!)
                    }
                    errorTitle = "Cannot Delete"
                    errorMessage = "An error occurred while deleting the item"
                    _event.emit(CartEvent.onItemRemoveError)
                }
            }
        }
    }

    fun checkout() {
//        viewModelScope.launch {
//            _uiState.value = CartUiState.Loading
//            val paymentDetails =
//                safeApiCall { foodApi.getPaymentIntent(PaymentIntentRequest(address.value!!.id!!)) }
//
//            when (paymentDetails) {
//                is ApiResponse.Success -> {
//                    paymentIntent = paymentDetails.data
//                    _event.emit(CartEvent.OnInitiatePayment(paymentDetails.data))
//                    _uiState.value = CartUiState.Success(cartResponse!!)
//                }
//
//                else -> {
//                    errorTitle = "Cannot Checkout"
//                    errorMessage = "An error occurred while checking out"
//                    _event.emit(CartEvent.showErrorDialog)
//                    _uiState.value = CartUiState.Success(cartResponse!!)
//                }
//            }
//
//        }
    }


    sealed class CartUiState() {
        object Nothing : CartUiState()
        object Loading : CartUiState()
        data class Error(val message: String) : CartUiState()
        data class Success(val data: CartResponse) : CartUiState()
    }

    sealed class CartEvent {
        object showErrorDialog : CartEvent()
        object onCheckout : CartEvent()
        object onQuantityUpdateError : CartEvent()
        object onItemRemoveError : CartEvent()
        object onAddressClicked : CartEvent()
//        data class OnInitiatePayment(val data: PaymentIntentResponse) : CartEvent()
        data class OrderSuccess(val orderId: String?) : CartEvent()

    }
}