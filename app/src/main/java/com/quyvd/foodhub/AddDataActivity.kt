package com.quyvd.foodhub

import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.firestore.FirebaseFirestore
import com.quyvd.foodhub.model.Animal
import kotlin.random.Random

class AddDataActivity : AppCompatActivity() {
    private lateinit var db: FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_add_data)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Dòng này để khởi tạo Firestore
        db = FirebaseFirestore.getInstance()


        findViewById<Button>(R.id.btAddData).setOnClickListener {
            addData()
        }

    }

    private fun getAnimals(): List<Animal> {
        var animals: MutableList<Animal> = mutableListOf()
        db.collection("animals")
            .get()
            .addOnSuccessListener { documentSnapshot ->

                // Lấy ra danh sách các đối tượng Animal

                animals = documentSnapshot.toObjects(Animal::class.java)

                // Duyêệt từng phần tử để xem các giá trị
                for (animal in animals) {
                    Log.d("QUYVD", "name: ${animal.name} - age: ${animal.age}")
                }
            }
            .addOnFailureListener { e ->
                Log.w("Firestore", "Error getting document", e)
            }

        // Trả về danh sách đối tượng Animal (Vì hàm có kiểu trả về là List<Animal>)
        return animals
    }

    private fun addData() {
        // Tạo 1 đối tượng để thêm vào Firestore
        // Tên random 5 ký tự và age cũng random từ 1 -> 100
        val dog = Animal(generateRandomString(5), Random.nextInt(1, 100))

        // Thêm vào Firestore
        // .collection("animals") là tên collection mà mình muốn thêm vào (gống như bảng trong SQL - ở đây là bảng animals)
        db.collection("animals")
            .add(dog)
            .addOnSuccessListener { documentReference ->
                // Thành công vì vào đây
                Log.d("QUYVD", "DocumentSnapshot added with ID: ${documentReference.id}")

                // Mỗi khi thêm thành công thì get lại list animals xem có cái mình mới thêm chưa
                getAnimals()
            }
            .addOnFailureListener { e ->
                //Thất bại thì vào đây
                Log.w("QUYVD", "Error adding animal: ${e.message}")
            }
    }

    private fun generateRandomString(length: Int): String {
        val charset = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"
        return (1..length)
            .map { charset.random() }
            .joinToString("")
    }
}