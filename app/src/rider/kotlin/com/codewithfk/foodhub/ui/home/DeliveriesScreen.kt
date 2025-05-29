package com.codewithfk.foodhub.ui.home

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import dagger.hilt.android.qualifiers.ApplicationContext

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeliveriesScreen(
    navController: NavController,
    homeViewModel: DeliveriesViewModel = hiltViewModel()
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        val uiState = homeViewModel.deliveriesState.collectAsStateWithLifecycle()
        when (val state = uiState.value) {
            is DeliveriesViewModel.DeliveriesState.Loading -> {
                Text("LOADING")
                CircularProgressIndicator()
            }

            is DeliveriesViewModel.DeliveriesState.Success -> {
                LazyColumn {
                    items(state.deliveries) { delivery ->

                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 4.dp)
                                .shadow(4.dp, shape = RoundedCornerShape(8.dp))
                                .clip(RoundedCornerShape(8.dp))
                                .background(Color.White)
                                .padding(8.dp)
                        ) {
                            Text(
                                text = delivery.customerAddress,
                                color = Color.Black,
                                style = MaterialTheme.typography.titleMedium
                            )
                            Text(
                                text = delivery.restaurantAddress,
                                color = Color.Black,
                                style = MaterialTheme.typography.bodyMedium
                            )
                            Text(
                                text = delivery.orderId,
                                color = Color.Gray,
                                style = MaterialTheme.typography.bodyMedium
                            )
                            Text(
                                text = "${delivery.estimatedDistance} km",
                                color = Color.Gray,
                                style = MaterialTheme.typography.bodyMedium
                            )
                            Text(
                                text = "$" + delivery.estimatedEarning.toString(),
                                color = Color.Green
                            )

                            val context = LocalContext.current

                            Row {
                                Button(onClick = {
                                    Toast.makeText(context ,"Accept button Clicked", Toast.LENGTH_SHORT).show()
                                    Log.d("DeliveriesScreen", "Accept button clicked" )
                                }) {
                                    Text(text = "Accept")
                                }
                                Button(onClick = {
                                    Log.d("DeliveriesScreen", "Decline button clicked" )
                                    Toast.makeText(context ,"Decline button Clicked", Toast.LENGTH_SHORT).show()
                                }) {
                                    Text(text = "Decline")
                                }
                            }
                        }
                    }
                }
            }

            is DeliveriesViewModel.DeliveriesState.Error -> {
                AlertDialog(
                    onDismissRequest = {},
                    content = {Text(state.message)}
                )
            }
        }
    }
}