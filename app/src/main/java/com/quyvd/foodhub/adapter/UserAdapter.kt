package com.quyvd.foodhub.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.google.firebase.auth.FirebaseUser
import com.quyvd.foodhub.R

class UserAdapter(private val userList: List<FirebaseUser>) : RecyclerView.Adapter<UserAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view){

        val tvUserEmail = view.findViewById<TextView>(R.id.tvUserEmail)
        val tvUserDisplayName = view.findViewById<TextView>(R.id.tvUserDisplayName)

        fun bind(item: FirebaseUser){

            tvUserEmail.text = item.email
            tvUserDisplayName.text = item.displayName

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_user,parent,false))
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
holder.bind(userList[position])
    }
}