package com.codewithfk.foodhub.ui.order_details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Polyline
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
fun OrderDetailsScreen(modifier: Modifier = Modifier) {
    val riderLocation = LatLng(37.7749, -122.4194) // Replace with rider's current location
    val customerLocation = LatLng(37.8000, -122.4194) // Replace with customer's current location

    val cameraPositionState = rememberCameraPositionState {
        this.position = CameraPosition.fromLatLngZoom(riderLocation, 15f)
    }

    Column {
        GoogleMap(
            modifier = modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState){
            Polyline(
                points = listOf(riderLocation, customerLocation),
                color = MaterialTheme.colorScheme.primary,
                width = 5f,
                zIndex = 200f
            )
        }
    }

    Button(onClick = {}) {
        Text("Deliver")
    }


}