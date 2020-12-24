package com.example.forum

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.forum.MainActivity.Companion.NICKNAME
import com.github.nkzawa.socketio.client.IO
import com.github.nkzawa.socketio.client.Socket
import java.net.URISyntaxException

class ChatBoxActivity : AppCompatActivity() {

    private lateinit var socket: Socket
    private lateinit var nickname: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_box)

        try {
            nickname = intent.getStringExtra("usernickname")!!
        } catch (e: Exception) {
            e.printStackTrace()
        }

        try {
            socket = IO.socket("http://192.168.0.3:3000")
            Log.d("success", "Connection successful")
        } catch (e: URISyntaxException) {
            e.printStackTrace()
            Log.d("fail", "Failed to connect")
        }

        socket.connect()

        socket.emit("join", nickname)
    }
}