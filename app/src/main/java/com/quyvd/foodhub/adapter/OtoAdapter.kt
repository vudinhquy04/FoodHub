package com.quyvd.foodhub.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.quyvd.foodhub.R
import com.quyvd.foodhub.model.Oto

class OtoAdapter(val otoList : List<Oto>, val itemClick : (Oto) -> Unit, val itemLongClick : (Oto)->Unit) : RecyclerView.Adapter<OtoAdapter.ViewHolder>() {

    inner class ViewHolder(view : View) : RecyclerView.ViewHolder(view) {

        var tvNameOto = view.findViewById<TextView>(R.id.tvNameOto)
        var tvCompanyOto = view.findViewById<TextView>(R.id.tvCompanyOto)
        var tvPriceOto = view.findViewById<TextView>(R.id.tvPriceOto)
        var tvId = view.findViewById<TextView>(R.id.tvId)

        fun bin(oto: Oto) {
            tvId.text = oto.id.toString()
            tvNameOto.text = oto.nameOto
            tvCompanyOto.text = oto.companyOto
            tvPriceOto.text = oto.priceOto.toString()

            itemView.setOnClickListener { itemClick(oto) }

            itemView.setOnLongClickListener {
                itemLongClick(oto)
                true
            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_oto, parent, false))
    }

    override fun getItemCount(): Int {
        return otoList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bin(otoList[position])
    }
}