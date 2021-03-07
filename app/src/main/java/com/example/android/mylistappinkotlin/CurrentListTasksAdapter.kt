package com.example.android.mylistappinkotlin

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class CurrentListTasksAdapter(private val context: Context):RecyclerView.Adapter<CurrentListTaskViewHolder>() {

    val example = arrayListOf<String>("1","2","3")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrentListTaskViewHolder {
        val view  = LayoutInflater.from(context).inflate(R.layout.current_list_section_view_holder,parent,false)
        return CurrentListTaskViewHolder(view)
    }

    override fun getItemCount(): Int {
        return example.size
    }

    override fun onBindViewHolder(holder: CurrentListTaskViewHolder, position: Int) {
        holder.count.text = (position+1).toString()
        holder.value.text = example[position]
    }
}