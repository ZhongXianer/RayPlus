package com.ksballetba.rayplus.ui.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ksballetba.rayplus.R

class TypeNameAdapter(val data: List<String>, val context: Context) :
    RecyclerView.Adapter<TypeNameAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView: View =
            LayoutInflater.from(context).inflate(R.layout.item_type_name, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return data.size
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var slTypeNameButton: TextView = itemView.findViewById(R.id.sl_type_button)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.d("hello",data[position])
        holder.slTypeNameButton.text = data[position]
    }
}