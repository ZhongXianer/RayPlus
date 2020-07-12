package com.ksballetba.rayplus.ui.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ksballetba.rayplus.R
import com.ksballetba.rayplus.data.bean.SelectTypeShowBodyBean


class SelectTypeAdapter(val data: List<SelectTypeShowBodyBean>, val context: Context) :
    RecyclerView.Adapter<SelectTypeAdapter.ViewHolder>() {


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val typeTitle: TextView = itemView.findViewById(R.id.sl_type_title)
        val typeNameList: RecyclerView = itemView.findViewById(R.id.sl_type_sub_list)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView: View =
            LayoutInflater.from(context).inflate(R.layout.item_select_type, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int =data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.d("hello","执行了")
        holder.typeTitle.text=data[position].type
        val typeNameAdapter=TypeNameAdapter(data[position].typeNames,context)
        holder.typeNameList.layoutManager=LinearLayoutManager(context)
        holder.typeNameList.adapter=typeNameAdapter
    }
}

