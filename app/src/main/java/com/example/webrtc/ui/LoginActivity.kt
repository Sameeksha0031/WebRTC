package com.example.webrtc.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.webrtc.R
import com.example.webrtc.databinding.ActivityLoginBinding
import com.example.webrtc.di.MsgRepository
import com.google.firebase.database.FirebaseDatabase
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    lateinit var binding : ActivityLoginBinding
    @Inject lateinit var msgRepository: MsgRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    private fun init() {
        binding.apply {
            signIn.setOnClickListener {
                msgRepository.login(
                    userName.text.toString(),
                    userPassword.text.toString()
                ){ isDone, reason ->
                    if(!isDone) {
                        Toast.makeText(this@LoginActivity,reason,Toast.LENGTH_SHORT).show()
                    } else {
                        startActivity(Intent(this@LoginActivity,MainActivity::class.java).apply {
                            putExtra("username",userName.text.toString())
                        })
                    }
                }
            }
        }
    }
}