package com.example.foodhub_android.data.models

data class DelieveriesListResponse(
    val `data`: List<Delieveries>
)

val delieveriesListResponse = DelieveriesListResponse(
    listOf(delivery1, delivery2, delivery3)
)