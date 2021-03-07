package com.example.android.mylistappinkotlin

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class CurrentListTasksAdapter(private val context: Context,private val name:String):RecyclerView.Adapter<CurrentListTaskViewHolder>() {

    private val listDataManager = ListDataManager(context)

    private val taskList = listDataManager.readCurrentTaskList(name)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrentListTaskViewHolder {
        val view  = LayoutInflater.from(context).inflate(R.layout.current_list_section_view_holder,parent,false)
        return CurrentListTaskViewHolder(view)
    }

    override fun getItemCount(): Int {
        return taskList.size
    }

    override fun onBindViewHolder(holder: CurrentListTaskViewHolder, position: Int) {
        holder.count.text = (position+1).toString()
        holder.value.text = taskList[position]
        holder.deleteTaskButton.setOnClickListener{
            listDataManager.deleteCurrentTask(name,taskList[position])
        }
    }

    fun isEmpty():Boolean{
        println(taskList.size)
        return taskList.size==0
    }

    fun printEvery (){
        println(taskList)
    }
}