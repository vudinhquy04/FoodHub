package com.quyvd.foodhub.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.quyvd.foodhub.R
import com.quyvd.foodhub.Student

class StudentAdapter(private var studentList : List<Student>) : RecyclerView.Adapter<StudentAdapter.ViewHolder>() {


    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvName: TextView = view.findViewById(R.id.tvName)
        val tvAge: TextView = view.findViewById(R.id.tvAge)
        val tvEmail: TextView = view.findViewById(R.id.tvEmail)

        fun bind(student: Student) {
            tvName.text = student.name
            tvAge.text = student.age.toString()
            tvEmail.text = student.email
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_student,parent,false))
    }

    override fun getItemCount(): Int {
        return studentList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(studentList[position])
    }
}