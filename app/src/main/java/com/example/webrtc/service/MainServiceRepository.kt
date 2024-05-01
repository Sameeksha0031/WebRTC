package com.example.webrtc.service

import android.content.Context
import android.content.Intent
import android.os.Build
import javax.inject.Inject

class MainServiceRepository @Inject constructor(
    private val context: Context
) {
    fun startService(username:String) {
        Thread{
            val intent = Intent(context,MainService::class.java)
            intent.putExtra("username",username)
            intent.action = MainServiceActions.START_SERVICE.name
            startService(intent)
        }.start()
    }
    private fun startService(intent: Intent) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(intent)
        } else {
            context.startService(intent)
        }
    }

    fun stopService() {
        val intent = Intent(context,MainService::class.java)
        intent.action = MainServiceActions.STOP_SERVICE.name
        startService(intent)
    }
}