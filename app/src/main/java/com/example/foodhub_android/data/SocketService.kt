package com.example.foodhub_android.data

import kotlinx.coroutines.flow.Flow

interface SocketService {

    fun connect()

    fun disconnect()

    fun sendMessage(message: String)

    fun message(): Flow<String>
}