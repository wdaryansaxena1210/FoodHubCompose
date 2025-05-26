package com.codewithfk.foodhub.ui.feature.food_item_details

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BasicTooltipBox
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.foodhub_android.R
import com.example.foodhub_android.data.models.FoodItem
import com.codewithfk.foodhub.ui.feature.restaurant_details.RestaurantDetails
import com.codewithfk.foodhub.ui.feature.restaurant_details.RestaurantDetailsHeader
import com.example.foodhub_android.ui.navigation.Cart
import com.example.foodhub_android.ui.theme.FoodHubAndroidTheme
import com.example.foodhub_android.ui.theme.Orange
import com.example.foodhub_android.ui.theme.Typography
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FoodDetailsScreen(
    modifier: Modifier = Modifier,
    foodItem: FoodItem,
    navController: NavController,
    onItemAddedToCart: () -> Unit,
    viewModel: FoodDetailsViewModel = hiltViewModel()
) {

    val showSuccessDialog = remember {
        mutableStateOf(false)
    }
    val showErrorDialog = remember {
        mutableStateOf(false)
    }
    val count = viewModel.quantity.collectAsStateWithLifecycle()
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()


    val isLoading = remember {
        mutableStateOf(false)
    }

    LaunchedEffect(Unit) {
        viewModel.event.collectLatest {
            when (it) {
                is FoodDetailsViewModel.FoodDetailsEvent.onAddToCart -> {
                    showSuccessDialog.value = true
                    onItemAddedToCart()
                }

                is FoodDetailsViewModel.FoodDetailsEvent.showErrorDialog -> {
                    showErrorDialog.value = true
                }

                is FoodDetailsViewModel.FoodDetailsEvent.goToCart -> {
                    navController.navigate(Cart)
                }

                else -> {}
            }
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        RestaurantDetailsHeader(imageUrl = foodItem.imageUrl,
            restaurantID = foodItem.id ?: "",
            onBackButton = {
                navController.popBackStack()
            }) {}
        RestaurantDetails(
            title = foodItem.name,
            description = foodItem.description,
            restaurantID = foodItem.id ?: "",
        )


        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = "$${foodItem.price}",
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.headlineLarge
            )
            Spacer(modifier = Modifier.weight(1f))
            FoodItemCounter(onCounterIncrement = {
                viewModel.incrementQuantity()
            }, onCounterDecrement = {
                viewModel.decrementQuantity()
            }, count = count.value
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        Button(
            onClick = {
                viewModel.addToCart(
                    restaurantId = foodItem.restaurantId, foodItemId = foodItem.id ?: ""
                )
                Log.d("FoodDetailsScreen", "onClick: ${foodItem.id}")
            },
            enabled = true,
            modifier = Modifier.padding(8.dp),
            colors = ButtonDefaults.buttonColors()
                .copy(containerColor = Orange, contentColor = Orange)

        ) {
            Row(
                modifier = Modifier
                    .background(Orange)
                    .padding(horizontal = 8.dp)
                    .clip(RoundedCornerShape(32.dp)),
                verticalAlignment = Alignment.CenterVertically
            ) {
                AnimatedVisibility(visible = true) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Image(
                            painter = painterResource(id = R.drawable.cart),
                            contentDescription = null
                        )
                        Spacer(modifier = Modifier.size(8.dp))
                        Text(
                            text = "Add to Cart".uppercase(),
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.White
                        )
                    }

                }
                AnimatedVisibility(visible = false) {
                    CircularProgressIndicator(modifier = Modifier.size(24.dp))
                }
            }
        }
    }

    if (showSuccessDialog.value) {
        ModalBottomSheet(onDismissRequest = { showSuccessDialog.value = false }) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "Item added to cart", style = MaterialTheme.typography.titleLarge
                )
                Spacer(modifier = Modifier.size(16.dp))
                Button(
                    onClick = {
                        showSuccessDialog.value = false
                        viewModel.goToCart()
                    }, modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth()
                ) {
                    Text(text = "Go to Cart")
                }

                Button(
                    onClick = {
                        showSuccessDialog.value = false
                        viewModel.goToCart()
                    }, modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth()
                ) {
                    Text(text = "OK")
                }
            }
        }
    }

    if (showErrorDialog.value) {
        ModalBottomSheet(onDismissRequest = { showSuccessDialog.value = false }) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "Unknown Error", style = MaterialTheme.typography.titleLarge
                )
                Spacer(modifier = Modifier.size(16.dp))
                Button(
                    onClick = {
                        showErrorDialog.value = false
                    }, modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth()
                ) {
                    Text(text = "Ok")
                }
            }
        }
    }
}

    @Composable
    fun FoodItemCounter(
        onCounterIncrement: () -> Unit,
        onCounterDecrement: () -> Unit,
        count: Int
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.height(100.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.add),
                contentDescription = null,
                modifier = Modifier
                    .clip(CircleShape)
                    .clickable { onCounterIncrement.invoke() }
                    .padding(top = 4.dp),
                contentScale = ContentScale.FillBounds
            )
            Spacer(modifier = Modifier.size(8.dp))
            Text(
                text = "${count}",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Spacer(modifier = Modifier.size(8.dp))
            Image(
                painter = painterResource(id = R.drawable.minus),
                contentDescription = null,
                modifier = Modifier
                    .clip(CircleShape)
                    .clickable { onCounterDecrement.invoke() }
                    .padding(top = 28.dp, end = 24.dp),
                contentScale = ContentScale.Crop,
            )
        }
    }
