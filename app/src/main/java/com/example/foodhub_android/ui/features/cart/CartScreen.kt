package com.example.foodhub_android.ui.features.cart

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.example.foodhub_android.R
import com.example.foodhub_android.data.models.CartItem
import com.example.foodhub_android.data.models.CheckoutDetails
import com.example.foodhub_android.ui.features.food_item_details.FoodItemCounter
import kotlinx.coroutines.flow.collectLatest
import java.text.NumberFormat

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreen(navController: NavController, viewModel: CartViewModel = hiltViewModel()) {


    val uiState = viewModel.uiState.collectAsStateWithLifecycle()
    val showErrorDialog = remember { mutableStateOf(false)}

    LaunchedEffect(key1 = true) {
        viewModel.event.collectLatest {
            when (it) {
                is CartViewModel.CartEvent.onItemRemoveError,
                is CartViewModel.CartEvent.onQuantityUpdateError,
                is CartViewModel.CartEvent.showErrorDialog -> {
                    showErrorDialog.value = true
                }

                is CartViewModel.CartEvent.onAddressClicked -> {
//                    navController.navigate(AddressList)
                }

                is CartViewModel.CartEvent.OrderSuccess -> {
//                    navController.navigate(OrderSuccess(it.orderId!!))
                }

//                is CartViewModel.CartEvent.OnInitiatePayment -> {
//                    PaymentConfiguration.init(navController.context, it.data.publishableKey)
//                    val customer = PaymentSheet.CustomerConfiguration(
//                        it.data.customerId,
//                        it.data.ephemeralKeySecret
//                    )
//                    val paymentSheetConfig = PaymentSheet.Configuration(
//                        merchantDisplayName = "FoodHub",
//                        customer = customer,
//                        allowsDelayedPaymentMethods = false,
//                    )
//
//                    // Initiate payment
//
//                    paymentSheet.presentWithPaymentIntent(
//                        it.data.paymentIntentClientSecret,
//                        paymentSheetConfig
//                    )
//                }

                else -> {

                }
            }
        }
    }

    Column {
        CartHeaderView(onBack = { navController.popBackStack() })
        Spacer(modifier = Modifier.size(16.dp))

        when (uiState.value) {
            is CartViewModel.CartUiState.Loading -> {
                Spacer(modifier = Modifier.size(16.dp))
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            is CartViewModel.CartUiState.Success -> {
                val data = (uiState.value as CartViewModel.CartUiState.Success).data

                LazyColumn {
                    items(data.items) { it ->
                        CartItemView(cartItem = it, onIncrement = { cartItem, _ ->
                            viewModel.incrementQuantity(cartItem)
                        }, onDecrement = { cartItem, _ ->
                            viewModel.decrementQuantity(cartItem)
                        }, onRemove = {
                            viewModel.removeItem(it)
                        })
                    }
                    item {
                        CheckoutDetailsView(data.checkoutDetails)
                    }
                }

            }

            is CartViewModel.CartUiState.Error -> {
                val message = (uiState.value as CartViewModel.CartUiState.Error).message
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(text = message)
                    Button(onClick = { }) {
                        Text("Retry")
                    }
                }
            }

            is CartViewModel.CartUiState.Nothing -> {

            }

        }

    }


    if (showErrorDialog.value) {
        ModalBottomSheet(onDismissRequest = { showErrorDialog.value = false }) {
            Column (horizontalAlignment = Alignment.CenterHorizontally){
                Text(text= viewModel.errorTitle, style = MaterialTheme.typography.headlineSmall )
                Spacer(modifier = Modifier.size(8.dp))
                Text(text= viewModel.errorMessage, style = MaterialTheme.typography.bodyMedium )
                Button(onClick = { showErrorDialog.value = false }) {
                    Text("OK")
                }
            }
        }
    }
}

@Composable
fun CartHeaderView(onBack: () -> Unit) {

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        IconButton(onClick = onBack) {
            Image(painter = painterResource(id = R.drawable.back), contentDescription = null)
        }
        Text(text = "Cart", style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.size(8.dp))
    }
}

@Composable
fun CartItemView(
    cartItem: CartItem,
    onIncrement: (CartItem, Int) -> Unit,
    onDecrement: (CartItem, Int) -> Unit,
    onRemove: (CartItem) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        AsyncImage(
            model = cartItem.menuItemId.imageUrl,
            contentDescription = null,
            modifier = Modifier
                .size(82.dp)
                .clip(RoundedCornerShape(12.dp)),
            contentScale = androidx.compose.ui.layout.ContentScale.Crop
        )
        Spacer(modifier = Modifier.size(12.dp))
        Column(verticalArrangement = Arrangement.Center) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = cartItem.menuItemId.name, style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.weight(1f))
                IconButton(
                    onClick = { onRemove.invoke(cartItem) }, modifier = Modifier.size(24.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Close,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(18.dp),
                    )
                }
            }
            Text(
                text = cartItem.menuItemId.description,
                maxLines = 1,
                color = Color.Gray,
                style = MaterialTheme.typography.bodySmall
            )
            Spacer(modifier = Modifier.size(8.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "$${cartItem.menuItemId.price}",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.weight(1f))
                FoodItemCounter(count = cartItem.quantity,
                    onCounterIncrement = { onIncrement.invoke(cartItem, cartItem.quantity) },
                    onCounterDecrement = { onDecrement.invoke(cartItem, cartItem.quantity) })
            }
        }
    }
}

@Composable
fun CheckoutDetailsView(checkoutDetails: CheckoutDetails) {
    Column {
        CheckoutRowItem(title = "SubTotal", value = checkoutDetails.subTotal, currency = "USD")
        CheckoutRowItem(title = "Tax", value = checkoutDetails.tax, currency = "USD")
        CheckoutRowItem(
            title = "Delivery Fee", value = checkoutDetails.deliveryFee, currency = "USD"
        )
        CheckoutRowItem(title = "Total", value = checkoutDetails.totalAmount, currency = "USD")
    }
}

@Composable
fun CheckoutRowItem(title: String, value: Double, currency: String) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
        ) {
            Text(text = title, style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = "$${NumberFormat.getCurrencyInstance().format(value)}",
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = currency,
                style = MaterialTheme.typography.titleMedium,
                color = Color.LightGray
            )
        }
        VerticalDivider()
    }

}

