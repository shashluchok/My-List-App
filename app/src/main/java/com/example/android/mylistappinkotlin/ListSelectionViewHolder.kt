package com.example.android.mylistappinkotlin

import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ListSelectionViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
    val listPosition  = itemView.findViewById(R.id.tv_item_number) as TextView
    val listTitle: TextView = itemView.findViewById(R.id.tv_item_string)
    val removeButton = itemView.findViewById(R.id.bt_remove) as Button
}