package com.quyvd.foodhub

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.quyvd.foodhub.adapter.StudentAdapter
import com.quyvd.foodhub.adapter.UserAdapter


class MainActivity : AppCompatActivity() {

//    private lateinit var recyclerViewUsers: RecyclerView
//    private lateinit var userAdapter: UserAdapter
//    private val userList = ArrayList<FirebaseUser>()
//    private val db = FirebaseFirestore.getInstance()

    private lateinit var recyclerViewStudents: RecyclerView
    private lateinit var studentAdapter: StudentAdapter
    private val studentList = ArrayList<Student>()
    private val db = FirebaseFirestore.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val btnLogout = findViewById<Button>(R.id.btnLogout)
        btnLogout.setOnClickListener {
            signOut()
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
        recyclerViewStudents = findViewById(R.id.recyclerViewStudent)
        studentAdapter = StudentAdapter(studentList)
        recyclerViewStudents.adapter = studentAdapter
        recyclerViewStudents.layoutManager = LinearLayoutManager(this)

        fetchStudents()

        val btnAddStudent = findViewById<Button>(R.id.btnAddStudent)
        btnAddStudent.setOnClickListener {
            val dialog = StudentDialogFragment()
            dialog.show(supportFragmentManager, "StudentDialogFragment")
        }


    }

    private fun signOut() {
        FirebaseAuth.getInstance().signOut()
    }

    fun fetchStudents() {
        db.collection("students")
            .get()
            .addOnSuccessListener { documents ->
                studentList.clear()
                for (document in documents) {
                    val student = document.toObject(Student::class.java)
                    studentList.add(student)
                }
                studentAdapter.notifyDataSetChanged()
            }
            .addOnFailureListener { exception ->
                // Handle failure
            }
    }

//    private fun fetchUsers() {
//
//        db.collection("users")
//            .get()
//            .addOnSuccessListener {
//                Log.d("QUYVD", "document: ${it.documents}")
//            }
//            .addOnFailureListener { exception ->
//                Log.d("QUYVD", "Error getting users: ", exception)
//            }
//    }
}