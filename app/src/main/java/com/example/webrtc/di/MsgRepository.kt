package com.example.webrtc.di

import javax.inject.Inject

class MsgRepository @Inject constructor(
    val firebaseClient: FirebaseClient
) {

    fun login(name : String, password : String, isDone : (Boolean,String?) -> Unit) {
        firebaseClient.login(name,password,isDone)
    }

    fun observerUserStatus(status:(List<Pair<String?,String>>)->Unit){
        firebaseClient.observeUserStatus(status)
    }
}