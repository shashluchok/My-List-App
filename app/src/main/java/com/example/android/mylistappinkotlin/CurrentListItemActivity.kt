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

        // Filling the list title with info from the recieved object
        taskListTitle.text = currentTaskListName.toUpperCase()


        //Setting an adapter based on the intent data
        recyclerViewTasks.layoutManager = LinearLayoutManager(this)
        adapterCurrent= CurrentListTasksAdapter(this,currentTaskListName)
        recyclerViewTasks.adapter = adapterCurrent

        //Checking if the Recycler View is empty
        checkIsEmpty()

        // Settin onClick Event to the button of the EditTextView (Creating and adding a task to the
        // chosen TaskList
        addTaskButton.setOnClickListener{
            if(editTextNewTask.text.length<2){
                Toast.makeText(this,"Слишком коротоко!",Toast.LENGTH_SHORT).show()

            }
            else{
                //Adding text from the EditView(EV) to the TaskList Array
                currentTaskListTasks.add(currentTaskListTasks.size ,editTextNewTask.text.toString())

                // Creating a new instance of TaskList with new data
                val taskList = TaskList(currentTaskListName,currentTaskListTasks)

                // Saving new instance in Shared Preferences
                /*
                Shared Preferences contains only unique keys, so I will just rewrite an already
                existing pair (key -> TaskList name,value -> tasks) with some new data (value - > tasks)
                 */
                dataManager.saveList(taskList)

                // Updating my adapter and setting it to this activity, so new data will
                // be shown
                adapterCurrent= CurrentListTasksAdapter(this,currentTaskListName)
                recyclerViewTasks.adapter = adapterCurrent

                // Checking if the Recycler View is Empty. It won't be, so the Recycler is
                // visible now
                checkIsEmpty()
            }
        }


    }

    // Overriding a method from ListDataManager.WorkOut interface, so it (ListDataManager) can now
    // call this method from its body

    override fun readAndSetLists() {
        // ListDataManager updates data in SharedPreferences, and , of course,
        // we need to update task list with new data come from it
        adapterCurrent = CurrentListTasksAdapter(this,currentTaskListName)
        recyclerViewTasks.adapter = adapterCurrent

        // We can remove and add some lists from ListDataManager class, that's why we always need
        // to check, if the Recycler View is empty now to show EmptyMessageTextView if it is.
        checkIsEmpty()
    }


    //A method, that shows EmptyMessameView, if the Recycler View is empty for the moment.
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