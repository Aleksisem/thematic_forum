package com.example.forum

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class MainActivity : AppCompatActivity() {
    private lateinit var button: Button
    private lateinit var nickname: EditText

    companion object {
        val NICKNAME = "usernickname"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button = findViewById(R.id.enterchat)
        nickname = findViewById(R.id.nickname)

        button.setOnClickListener {
            if (nickname.text.toString().isNotEmpty()) {
                val intent = Intent(this, ChatBoxActivity::class.java)
                intent.putExtra(NICKNAME, nickname.text.toString())
                startActivity(intent)
            }
        }
    }
}