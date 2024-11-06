package com.quyvd.foodhub

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

class OtoDialogUpdateFragment(private val oto : Oto) : DialogFragment() {

    private lateinit var db : FirebaseFirestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_oto_dialog_update, container, false)

        db = FirebaseFirestore.getInstance()

        val btnSave = view.findViewById<Button>(R.id.btnSaveOto)
        val edtNameOto = view.findViewById<EditText>(R.id.edtNameOtoUpdate)
        val edtHangOto = view.findViewById<EditText>(R.id.edtHangOtoUpdate)
        val edtGiaOto = view.findViewById<EditText>(R.id.edtGiaOtoUpdate)
        val edtId = view.findViewById<EditText>(R.id.edtIdUpdate)

        edtId.setText("${oto.id}")
        edtNameOto.setText(oto.nameOto)
        edtHangOto.setText(oto.hangOto)
        edtGiaOto.setText("${oto.giaOto}")

        btnSave.setOnClickListener {
            val id = edtId.text.toString().toInt()
            val nameOto = edtNameOto.text.toString()
            val hangOto = edtHangOto.text.toString()
            val giaOto = edtGiaOto.text.toString().toInt()

            saveOto(id,nameOto,hangOto,giaOto)
        }





        return view

    }

    private fun saveOto(id : Int, nameOto : String, hangOto : String, giaOto : Int){

        val oto = Oto(id = id,nameOto =nameOto, hangOto = hangOto, giaOto = giaOto)

        db.collection("otos").document("${oto.id}").set(oto).addOnSuccessListener {
            dismiss()
            (activity as OtoActivity).fetchOto()
        }.addOnFailureListener { e ->
            Log.d("QUYVD", "Erroll: ${{e.message}}")
        }

    }

}