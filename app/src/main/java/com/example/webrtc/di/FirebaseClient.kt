package com.example.webrtc.di

import android.util.Log
import com.example.webrtc.utils.FirebaseFieldName.PASSWORD
import com.example.webrtc.utils.FirebaseFieldName.STATUS
import com.example.webrtc.utils.UserStatus
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.gson.Gson
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirebaseClient @Inject constructor(
    private val dbRef : DatabaseReference,
    private val gson : Gson
){
    private val TAG ="MainActivity"

    private var currentUserName : String ?= null
    fun setCurrentUserName(currentUserName : String) {
        this.currentUserName = currentUserName
    }

    fun login(name : String, password : String, isDone : (Boolean,String?) -> Unit){
        dbRef.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.hasChild(name)){
                    val dbPassword = snapshot.child(name).child(PASSWORD).value
                    if(password == dbPassword) {
                        dbRef.child(name).child(STATUS).setValue(UserStatus.ONLINE)
                            .addOnCompleteListener {
                                setCurrentUserName(name)
                                isDone(true, null)
                            }.addOnFailureListener {
                                isDone(false,"${it.message}")
                            }
                    } else {
                        isDone(false,"Password is wrong")
                    }
                } else {
                    dbRef.child(name).child(PASSWORD).setValue(password)
                        .addOnCanceledListener {
                            dbRef.child(name).child(STATUS).setValue(UserStatus.ONLINE)
                                .addOnFailureListener {
                                    isDone(false,"${it.message}")
                                }.addOnCompleteListener {
                                    setCurrentUserName(name)
                                    isDone(true,null)
                            }
                        }.addOnFailureListener {
                            isDone(false,"${it.message}")
                        }
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }

        })
    }

    fun observeUserStatus(status: (List<Pair<String?, String>>) -> Unit) {
        dbRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val list = snapshot.children.filter { it.key != currentUserName }.map{
                    it.key to it.child(STATUS).value.toString()
                }
                status(list)
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
    }
}