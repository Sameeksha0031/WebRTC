package com.example.webrtc.service

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.webrtc.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainService : Service() {

    private var isServiceRunning = false
    private var userName : String ?= null

    private lateinit var notificationManager: NotificationManager

    companion object {
        private const val NOTIFICATION_ID = 1
        private const val CHANNEL_ID = "ForegroundServiceChannel"
    }

    override fun onCreate() {
        super.onCreate()
        Log.d("MainActivity","MainService onCreate")
        notificationManager = getSystemService(NotificationManager::class.java)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d("MainActivity","MainService onStartCommand = ${intent.toString()}")
        intent?.let {incomingIntent ->
            when(incomingIntent.action){
                MainServiceActions.START_SERVICE.name -> {
                    Log.d("MainActivity","Calling handleStartService")
                    handleStartService(incomingIntent)
                }
                    else -> Unit
            }
        }
        return START_STICKY
    }

    private fun handleStartService(incomingIntent: Intent) {
        Log.d("MainActivity","Called handleStartService isServiceRunning = $isServiceRunning")
        if(!isServiceRunning) {
            Log.d("MainActivity","Called handleStartService isServiceRunning = $isServiceRunning")
            isServiceRunning = true
            userName = incomingIntent.getStringExtra("username")
            startServiceWithNotification()
        }
    }

    @SuppressLint("ForegroundServiceType")
    private fun startServiceWithNotification() {
        Log.d("MainActivity","startServiceWithNotification")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            Log.d("MainActivity","MainService startServiceWithoutNotification")
            val notificationChannel = NotificationChannel(
                CHANNEL_ID,"foreground",NotificationManager.IMPORTANCE_HIGH
            )
            notificationManager.createNotificationChannel(notificationChannel)
            val notification = NotificationCompat.Builder(
                this,CHANNEL_ID
            ).setContentTitle("Foreground Service")
                .setContentText("Service is running...")
                .setSmallIcon(R.mipmap.ic_launcher)

            startForeground(NOTIFICATION_ID,notification.build())
        }
    }

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }
}