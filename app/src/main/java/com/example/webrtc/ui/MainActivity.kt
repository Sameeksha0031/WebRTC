package com.example.webrtc.ui

import android.media.MediaDrm.LogMessage
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.webrtc.MainRecyclerViewAdapter
import com.example.webrtc.databinding.ActivityMainBinding
import com.example.webrtc.di.MsgRepository
import com.example.webrtc.service.MainService
import com.example.webrtc.service.MainServiceRepository
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), MainRecyclerViewAdapter.Listener {
    private val TAG = "MainActivity"

    private lateinit var binding: ActivityMainBinding
    private var username : String ?= null
    private var mainAdapter: MainRecyclerViewAdapter? = null
    @Inject
    lateinit var msgRepository: MsgRepository

    @Inject
    lateinit var mainServiceRepository: MainServiceRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    private fun init() {
        username = intent.getStringExtra("username")
        if(username == null) finish()

        subscribeObserver()
        startService()
    }

    private fun subscribeObserver(){
        mainAdapter = MainRecyclerViewAdapter(this)
        val layoutManager = LinearLayoutManager(this@MainActivity)
        binding.callList.apply {
            setLayoutManager(layoutManager)
            adapter = mainAdapter
        }
        msgRepository.observerUserStatus {
            Log.d(TAG,"subscriberObserver = $it")
            mainAdapter?.updateList(it)
        }
    }

    private fun startService(){
        Log.d("MainActivity","username = $username")
        username?.let { mainServiceRepository.startService(it) }
    }

    override fun onVideoCallClicked(username: String) {

    }

    override fun onAudioCallClicked(username: String) {

    }
}