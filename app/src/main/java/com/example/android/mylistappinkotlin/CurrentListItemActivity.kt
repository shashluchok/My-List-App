package com.example.android.mylistappinkotlin

import android.os.Bundle
import android.widget.ImageButton
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView

class CurrentListItemActivity : AppCompatActivity() {

    companion object {
        const val INTENT_LIST_KEY = "INTENT_LIST_KEY"
    }

    private lateinit var taskListTitle: TextView
    private lateinit var titleLayout: RelativeLayout
    private lateinit var emptyMessageTextView: TextView
    private lateinit var currentTaskListName: String
    private lateinit var currentTaskListTasks: ArrayList<String>
    private lateinit var recyclerViewTasks:RecyclerView
    private lateinit var addTaskButton:ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_current_list_item)

        //Hooking up views
        taskListTitle = findViewById(R.id.tv_current_task_list_title)
        titleLayout = findViewById(R.id.layout_title)
        emptyMessageTextView = findViewById(R.id.tv_no_tasks)
        recyclerViewTasks = findViewById(R.id.recycler_view_current_tasks)
        addTaskButton = findViewById(R.id.bt_add_task)

        // Taking current TaskList object from the intent
        val currentTaskList: TaskList = intent.getParcelableExtra<TaskList>(INTENT_LIST_KEY) as TaskList
        currentTaskListName = currentTaskList.name
        currentTaskListTasks = currentTaskList.tasks

        if (!currentTaskListTasks.isEmpty())emptyMessageTextView.isVisible=false




        // Filling activity with info from the recieved object
        taskListTitle.text = currentTaskList.name


    }
}