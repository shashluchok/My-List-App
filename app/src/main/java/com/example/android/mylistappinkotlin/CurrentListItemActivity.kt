package com.example.android.mylistappinkotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView

class CurrentListItemActivity : AppCompatActivity() {

    companion object{
        const val INTENT_LIST_KEY = "INTENT_LIST_KEY"
    }

    private lateinit var taskListTitle:TextView
    private lateinit var titleLayout:LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_current_list_item)
        val currentTaskList:TaskList = intent.getParcelableExtra<TaskList>(INTENT_LIST_KEY) as TaskList
        taskListTitle = findViewById(R.id.tv_current_task_list_title)
        titleLayout = findViewById(R.id.layout_title)
        taskListTitle.text = currentTaskList.name
        ;

    }
}