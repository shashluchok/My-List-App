package com.example.android.mylistappinkotlin

import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CurrentListTaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val count = itemView.findViewById<TextView>(R.id.tv_current_task_list_item_count)
    val value = itemView.findViewById<TextView>(R.id.tv_current_task_list_item_value)
    val deleteTaskButton = itemView.findViewById<ImageButton>(R.id.button_delete_current_task)
}