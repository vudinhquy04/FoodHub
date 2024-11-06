package com.quyvd.foodhub

import android.app.Activity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import com.google.firebase.firestore.FirebaseFirestore
import com.quyvd.foodhub.model.Oto

class OtoDialogFragment : DialogFragment() {

    private lateinit var db: FirebaseFirestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_oto_dialog, container, false)

        db = FirebaseFirestore.getInstance()

        val btnAddOto = view.findViewById<Button>(R.id.btnAddOto)
        val edtNameOto = view.findViewById<EditText>(R.id.edtNameOto)
        val edtHangOto = view.findViewById<EditText>(R.id.edtHangOto)
        val edtGiaOto = view.findViewById<EditText>(R.id.edtGiaOto)


        btnAddOto.setOnClickListener {
            val nameOto = edtNameOto.text.toString()
            val hangOto = edtHangOto.text.toString()
            val giaOto = edtGiaOto.text.toString().toInt()
            addOto(nameOto,hangOto,giaOto)
        }

        return view

    }

  private  fun addOto(nameOto : String, hangOto : String, giaOto : Int){

        val oto = Oto(nameOto = nameOto, hangOto = hangOto, giaOto = giaOto)

        db.collection("otos").add(oto)
            .addOnSuccessListener {
                dismiss()
                (activity as OtoActivity).fetchOto()
            }.addOnFailureListener { e ->
                Log.d("QUYVD", "Erroll: ${{e.message}}")
            }
    }



}