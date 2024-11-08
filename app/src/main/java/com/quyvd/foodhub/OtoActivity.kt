package com.quyvd.foodhub

import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.quyvd.foodhub.adapter.OtoAdapter
import com.quyvd.foodhub.model.Oto
import javax.annotation.processing.Generated
import kotlin.random.Random

class OtoActivity : AppCompatActivity() {
    private lateinit var db : FirebaseFirestore

    private lateinit var rvOto : RecyclerView
    private lateinit var otoAdapter: OtoAdapter
    private var otoList = ArrayList<Oto>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_oto)

        db = FirebaseFirestore.getInstance()

        rvOto = findViewById(R.id.rvOto)
        otoAdapter = OtoAdapter(otoList, { oto -> itemClick(oto) }, {oto -> itemLongClick(oto) })
        rvOto.adapter = otoAdapter
        rvOto.layoutManager = LinearLayoutManager(this)

        fetchOto()

        findViewById<Button>(R.id.btAddOto).setOnClickListener {
            val otoDialog = OtoDialogFragment()
            otoDialog.show(supportFragmentManager, "OtoDialogFragment")
        }

    }
    private fun itemClick(oto: Oto){

        val dialogUpdateOto = OtoDialogUpdateFragment(oto)
        dialogUpdateOto.show(supportFragmentManager,"OtoDialogUpdateFragment")
    }

    private fun itemLongClick(oto: Oto){

        showDialogDelete(oto.id)
    }

//    private fun getOtos() : List<Oto>{
//        var otos : MutableList<Oto> = mutableListOf()
//
//        db.collection("otos")
//            .get()
//            .addOnSuccessListener { documentSnapshot ->
//
//                otos = documentSnapshot.toObjects(Oto::class.java)
//
//                for (oto in otos){
//                    Log.d("QUYVD", "Oto: ${oto.nameOto} - Hãng: ${oto.hangOto} - Giá: ${oto.giaOto}")
//                }
//            }
//            .addOnFailureListener { e ->
//                Log.w("Firestore", "Error getting document", e)
//            }
//        return otos
//    }
//
//    private fun addOto(){
//
//        val oto = Oto(generateRamdomString(6),generateRamdomString(3), Random.nextInt(1,100))
//
//            db.collection("otos").add(oto).addOnSuccessListener {
//                doccumentReference ->
//                Log.d("QUYVD", "DocumentSnapshot added with ID: ${doccumentReference.id}")
//                getOtos()
//            }.addOnFailureListener { e ->
//                Log.w("QUYVD", "Error adding Ôti ${e.message}")
//            }
//    }
//
//    private fun generateRamdomString(length : Int) :String {
//        var charset = "ABCDEFGHIJKLMNOPQRSTUVXYZabcdefghijklmnopqrstuvwxyz0123456789"
//        return (1..length).map{charset.random()}.joinToString("")
//
//        }
    fun fetchOto() {
        db.collection("otos").get().addOnSuccessListener { documents ->
            otoList.clear()
            for (document in documents) {
                val oto = document.toObject(Oto::class.java)
                otoList.add(oto)
            }
            otoAdapter.notifyDataSetChanged()
        }.addOnFailureListener { e ->
            Log.d("QUYVD", "Errol: ${e.message}")
        }

    }

    fun showDialogDelete(id : Int){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Xác nhận xóa")
        builder.setMessage("Bạn chắc chắn muốn xóa xe oto này?")

        builder.setPositiveButton("Yes"){ dialog, _->
            deleteOto(id)
            dialog.dismiss()
        }
        builder.setNegativeButton("No"){dialog,_->
            dialog.dismiss()
        }
        val dialog = builder.create()
        dialog.show()
    }

    fun deleteOto(id: Int){
        val db = FirebaseFirestore.getInstance()
        val oto = Oto(id)
        db.collection("otos").document("${oto.id}").delete()
            .addOnSuccessListener {
                otoList.clear()
                Log.d("QUYVD", "Delete Success ")
                otoAdapter.notifyDataSetChanged()
            }.addOnFailureListener { e ->
                Log.d("QUYVD", "Errol ${e.message}")
            }
    }

    }