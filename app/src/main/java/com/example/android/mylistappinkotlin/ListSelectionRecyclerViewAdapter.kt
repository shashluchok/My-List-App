package com.example.android.mylistappinkotlin

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.core.content.ContextCompat.startActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView

class ListSelectionRecyclerViewAdapter(private val lists: ArrayList<TaskList>, private val context: Context): RecyclerView.Adapter<ListSelectionViewHolder>() {

    private val listDataManager = ListDataManager(context)

    interface openCurrentTaskListListener {
        fun openCurrentTaskList(list:TaskList)
    }

    private val listener:openCurrentTaskListListener = context as openCurrentTaskListListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListSelectionViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.list_selection_view_holder,parent,false)
        return ListSelectionViewHolder(view)
    }

    override fun getItemCount(): Int {
       return lists.size

    }

    override fun onBindViewHolder(holder: ListSelectionViewHolder, position: Int) {

        holder.listPosition.text=(position+1).toString()
        holder.listTitle.text=(lists[position].name)
        holder.removeButton.setOnClickListener{
            listDataManager.deleteList(holder.listTitle.text.toString())
        }
        holder.openButton.setOnClickListener{
            listener.openCurrentTaskList(lists[position])
        }


    }





}