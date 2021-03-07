package com.example.android.mylistappinkotlin

import android.os.Bundle
import android.text.Editable
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class CurrentListItemActivity : AppCompatActivity(),ListDataManager.WorkOut {

    private val dataManager = ListDataManager(this)


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
    private lateinit var editTextNewTask:EditText
    private lateinit var adapterCurrent:CurrentListTasksAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_current_list_item)


        //Hooking up views
        taskListTitle = findViewById(R.id.tv_current_task_list_title)
        titleLayout = findViewById(R.id.layout_title)
        emptyMessageTextView = findViewById(R.id.tv_no_tasks)
        recyclerViewTasks = findViewById(R.id.recycler_view_current_tasks)
        addTaskButton = findViewById(R.id.bt_add_task)
        editTextNewTask = findViewById(R.id.et_new_task)

        // Taking current TaskList object from the intent
        val currentTaskList: TaskList = intent.getParcelableExtra<TaskList>(INTENT_LIST_KEY) as TaskList
        currentTaskListName = currentTaskList.name
        currentTaskListTasks = currentTaskList.tasks

        recyclerViewTasks.layoutManager = LinearLayoutManager(this)
        adapterCurrent= CurrentListTasksAdapter(this,currentTaskListName)
        recyclerViewTasks.adapter = adapterCurrent
        checkIsEmpty()

        addTaskButton.setOnClickListener{
            if(editTextNewTask.text.length<2){
                Toast.makeText(this,"Слишком коротоко!",Toast.LENGTH_SHORT).show()

            }
            else{
                currentTaskListTasks.add(editTextNewTask.text.toString())
                val taskList = TaskList(currentTaskListName,currentTaskListTasks)
                dataManager.saveList(taskList)
                adapterCurrent= CurrentListTasksAdapter(this,currentTaskListName)
                recyclerViewTasks.adapter = adapterCurrent
                checkIsEmpty()
            }
        }









        // Filling activity with info from the recieved object
        taskListTitle.text = currentTaskListName.toUpperCase()

    }

    override fun readAndSetLists() {
        adapterCurrent = CurrentListTasksAdapter(this,currentTaskListName)
        recyclerViewTasks.adapter = adapterCurrent
        checkIsEmpty()
        adapterCurrent.printEvery()
    }

    private fun checkIsEmpty(){
        if (!adapterCurrent.isEmpty()){
            recyclerViewTasks.isVisible = true
            emptyMessageTextView.isVisible=false
        }
        else{
            recyclerViewTasks.isVisible = false
            emptyMessageTextView.isVisible=true
        }
    }
}