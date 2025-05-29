package com.example.foodhub_android.data.models


data class Delieveries(
    val createdAt: String,
    val customerAddress: String,
    val estimatedDistance: Double,
    val estimatedEarning: Double,
    val orderAmount: Double,
    val orderId: String,
    val restaurantAddress: String,
    val restaurantName: String
)


val delivery1 = Delieveries(
    createdAt = "2025-05-25T14:30:00Z",
    customerAddress = "123 Main St, Springfield",
    estimatedDistance = 4.5,
    estimatedEarning = 8.75,
    orderAmount = 27.99,
    orderId = "ORD123456",
    restaurantAddress = "456 Elm St, Springfield",
    restaurantName = "Pizza Palace"
)

val delivery2 = Delieveries(
    createdAt = "2025-05-26T09:15:00Z",
    customerAddress = "789 Oak Ave, Lincoln",
    estimatedDistance = 6.2,
    estimatedEarning = 10.50,
    orderAmount = 35.49,
    orderId = "ORD123457",
    restaurantAddress = "101 Maple Rd, Lincoln",
    restaurantName = "Burger Barn"
)

val delivery3 = Delieveries(
    createdAt = "2025-05-26T12:00:00Z",
    customerAddress = "321 Pine Ln, Centerville",
    estimatedDistance = 3.8,
    estimatedEarning = 7.25,
    orderAmount = 19.99,
    orderId = "ORD123458",
    restaurantAddress = "202 Cedar St, Centerville",
    restaurantName = "Sushi Stop"
)
