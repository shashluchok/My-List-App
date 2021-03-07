package com.example.android.mylistappinkotlin

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView

class ListSelectionRecyclerViewAdapter(private val lists: ArrayList<TaskList>, private val context: Context): RecyclerView.Adapter<ListSelectionViewHolder>() {

    private val listDataManager = ListDataManager(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListSelectionViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_selection_view_holder,parent,false)
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
            moveToListActivity()
        }

    }

    fun addList(list:TaskList){
        lists.add(list)
        notifyItemInserted(lists.size-1)
    }

    private fun moveToListActivity(){
        val intent:Intent = Intent(context,CurrentListItemActivity::class.java)
        startActivity(context,intent,null)
    }

}