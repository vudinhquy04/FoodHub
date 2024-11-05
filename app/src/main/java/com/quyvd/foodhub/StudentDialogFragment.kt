package com.quyvd.foodhub

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import com.google.firebase.firestore.FirebaseFirestore

class StudentDialogFragment : DialogFragment() {

    private lateinit var db: FirebaseFirestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_student_dialog, container, false)
        db = FirebaseFirestore.getInstance()

        val btnAddStudent = view.findViewById<Button>(R.id.btnAddStudent)
        val edtName = view.findViewById<EditText>(R.id.edtName)
        val edtAge = view.findViewById<EditText>(R.id.edtAge)
        val edtEmail = view.findViewById<EditText>(R.id.edtEmail)

        btnAddStudent.setOnClickListener {
            val name = edtName.text.toString()
            val age = edtAge.text.toString().toInt()
            val email = edtEmail.text.toString()
            addStudent(name, age, email)
        }

        return view
    }

    private fun addStudent(name: String, age: Int, email: String) {
        val student = Student(name = name, age = age, email = email)
        db.collection("students").add(student)
            .addOnSuccessListener {
                dismiss()
                (activity as MainActivity).fetchStudents()
            }
            .addOnFailureListener { e ->
                // Handle failure
            }
    }
}