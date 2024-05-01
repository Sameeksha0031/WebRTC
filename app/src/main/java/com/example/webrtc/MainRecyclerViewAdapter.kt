package com.example.webrtc

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.webrtc.databinding.RecyclerCardLayoutBinding

class MainRecyclerViewAdapter(private val listener:Listener) : RecyclerView.Adapter<MainRecyclerViewAdapter.MainRecyclerViewHolder>() {

    private var usersList:List<Pair<String?,String>>?=null
    fun updateList(list:List<Pair<String?,String>>){
        this.usersList = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainRecyclerViewHolder {
        val binding = RecyclerCardLayoutBinding.inflate(
            LayoutInflater.from(parent.context),parent,false
        )
        return MainRecyclerViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return usersList?.size?:0
    }

    override fun onBindViewHolder(holder: MainRecyclerViewHolder, position: Int) {
        usersList?.let { list->
            val user = list[position]
            holder.bind(user,{
                listener.onVideoCallClicked(it)
            }) {
                listener.onAudioCallClicked(it)
            }
        }
    }

    interface  Listener {
        fun onVideoCallClicked(username:String)
        fun onAudioCallClicked(username:String)
    }



    class MainRecyclerViewHolder(private val binding: RecyclerCardLayoutBinding):
        RecyclerView.ViewHolder(binding.root){
        private val context = binding.root.context

        fun bind(
            user: Pair<String?, String>,
            videoCallClicked:(String) -> Unit,
            audioCallClicked:(String)-> Unit
        ){
            binding.apply {
                when (user.second) {
                    "ONLINE" -> {
                        videoCallBtn.isVisible = true
                        audioCallBtn.isVisible = true
                        videoCallBtn.setOnClickListener {
                            user.first?.let { it1 -> videoCallClicked.invoke(it1) }
                        }
                        audioCallBtn.setOnClickListener {
                            user.first?.let { it1 -> audioCallClicked.invoke(it1) }
                        }
                        statusTv.setTextColor(context.resources.getColor(R.color.accept, null))
                        statusTv.text = "Online"
                    }
                    "OFFLINE" -> {
                        videoCallBtn.isVisible = false
                        audioCallBtn.isVisible = false
                        statusTv.setTextColor(context.resources.getColor(R.color.decline, null))
                        statusTv.text = "Offline"
                    }
                    "IN_CALL" -> {
                        videoCallBtn.isVisible = false
                        audioCallBtn.isVisible = false
                        statusTv.setTextColor(context.resources.getColor(R.color.user_status, null))
                        statusTv.text = "In Call"
                    }
                }

                userNameTv.text = user.first
            }



        }



    }
}

