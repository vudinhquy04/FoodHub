package com.quyvd.foodhub

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
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
        val edtCompanyOto = view.findViewById<EditText>(R.id.edtCompanyOtoUpdate)
        val edtPriceOto = view.findViewById<EditText>(R.id.edtPriceOtoUpdate)
        val edtId = view.findViewById<EditText>(R.id.edtIdUpdate)

        edtId.setText("${oto.id}")
        edtId.isFocusable = false
        edtId.isFocusableInTouchMode = false

        edtNameOto.setText(oto.nameOto)
        edtCompanyOto.setText(oto.companyOto)
        edtPriceOto.setText("${oto.priceOto}")

        btnSave.setOnClickListener {
            val id = edtId.text.toString().toInt()
            val nameOto = edtNameOto.text.toString()
            val companyOto = edtCompanyOto.text.toString()
            val priceOto = edtPriceOto.text.toString().toInt()

            saveOto(id,nameOto,companyOto,priceOto)
        }

        return view

    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT)
    }

    private fun saveOto(id : Int, nameOto : String, companyOto : String, priceOto : Int){

        val oto = Oto(id = id,nameOto =nameOto, companyOto = companyOto, priceOto = priceOto)

        db.collection("otos").document("${oto.id}").set(oto).addOnSuccessListener {
            dismiss()
            (activity as OtoActivity).fetchOto()
        }.addOnFailureListener { e ->
            Log.d("QUYVD", "Erroll: ${{e.message}}")
        }

    }

}