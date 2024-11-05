package com.quyvd.foodhub

import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.firestore.FirebaseFirestore
import com.quyvd.foodhub.model.Oto
import javax.annotation.processing.Generated
import kotlin.random.Random

class OtoActivity : AppCompatActivity() {
    private lateinit var db : FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_oto)

        db = FirebaseFirestore.getInstance()


        findViewById<Button>(R.id.btAddOto).setOnClickListener {

            addOto()
        }

    }

    private fun getOtos() : List<Oto>{
        var otos : MutableList<Oto> = mutableListOf()

        db.collection("otos")
            .get()
            .addOnSuccessListener { documentSnapshot ->

                otos = documentSnapshot.toObjects(Oto::class.java)

                for (oto in otos){
                    Log.d("QUYVD", "Oto: ${oto.nameOto} - Hãng: ${oto.hangOto} - Giá: ${oto.giaOto}")
                }
            }
            .addOnFailureListener { e ->
                Log.w("Firestore", "Error getting document", e)
            }
        return otos
    }

    private fun addOto(){

        val oto = Oto(generateRamdomString(6),generateRamdomString(3), Random.nextInt(1,100))

            db.collection("otos").add(oto).addOnSuccessListener {
                doccumentReference ->
                Log.d("QUYVD", "DocumentSnapshot added with ID: ${doccumentReference.id}")
                getOtos()
            }.addOnFailureListener { e ->
                Log.w("QUYVD", "Error adding Ôti ${e.message}")
            }
    }

    private fun generateRamdomString(length : Int) :String {
        var charset = "ABCDEFGHIJKLMNOPQRSTUVXYZabcdefghijklmnopqrstuvwxyz0123456789"
        return (1..length).map{charset.random()}.joinToString("")

        }
    }