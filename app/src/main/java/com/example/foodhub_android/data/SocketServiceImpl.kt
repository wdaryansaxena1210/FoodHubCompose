package com.example.foodhub_android.data

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener

class SocketServiceImpl : SocketService {

    var webSocket : WebSocket? = null

    override fun connect() {

        //THEORY : client initiate the actual call. call ofc needs a request header,body, etc.
        //
        val builder = Request.Builder().url("ws://localhost:8080").build()
        val client = OkHttpClient.Builder().build()
        webSocket = client.newWebSocket(builder, createWebSocketListener() )
    }

    private fun createWebSocketListener(): WebSocketListener {
        return object : WebSocketListener() {
            override fun onOpen(webSocket: WebSocket, response: Response) {
                super.onOpen(webSocket, response)
            }

            override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
                super.onClosed(webSocket, code, reason)
            }

            override fun onMessage(webSocket: WebSocket, text: String) {
                super.onMessage(webSocket, text)
                CoroutineScope(Dispatchers.IO).launch {

                }
            }
        }
    }

    override fun disconnect() {
        webSocket?.close(1000, "did its job")
    }

    override fun sendMessage(message: String) {

    }

    override fun message(): Flow<String> {

        return flow{}
    }

}