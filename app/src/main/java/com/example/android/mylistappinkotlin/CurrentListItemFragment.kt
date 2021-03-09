package com.example.android.mylistappinkotlin

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class CurrentListItemFragment : Fragment() {

    private lateinit var layout:View




    companion object {
        fun newInstance ():CurrentListItemFragment{
            return CurrentListItemFragment()
        }
    }

    private lateinit var taskListTitle: TextView
    private lateinit var titleLayout: RelativeLayout
    private lateinit var emptyMessageTextView: TextView
    private lateinit var currentTaskListName: String
    private lateinit var currentTaskListTasks: ArrayList<String>
    private lateinit var recyclerViewTasks: RecyclerView
    private lateinit var addTaskButton: ImageButton
    private lateinit var editTextNewTask: EditText
    private lateinit var editTextLayout: LinearLayout
    private lateinit var adapterCurrent:CurrentListTasksAdapter
    private lateinit var myContext:Context
    private lateinit var  dataManager: ListDataManager
    private lateinit var currentTaskList:TaskList

    override fun onAttach(context: Context) {
        super.onAttach(context)
        myContext = context
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        layout = inflater.inflate(R.layout.fragment_current_list_item, container, false)
        layout.startAnimation(AnimationUtils.loadAnimation(context,R.anim.fading_in_animation))
        //Hooking up views
        taskListTitle = layout.findViewById(R.id.tv_current_task_list_title)
        titleLayout = layout.findViewById(R.id.layout_title)
        emptyMessageTextView = layout.findViewById(R.id.tv_no_tasks)
        recyclerViewTasks = layout.findViewById(R.id.recycler_view_current_tasks)
        addTaskButton = layout.findViewById(R.id.bt_add_task)
        editTextNewTask = layout.findViewById(R.id.et_new_task)
        editTextLayout = layout.findViewById(R.id.et_layout)

        dataManager = ListDataManager(myContext)


        if(savedInstanceState!=null){
            currentTaskList = savedInstanceState.getParcelable<TaskList>(MainActivity.TASKLIST_KEY)!!
        }

        // Taking current TaskList object from the setCurrentTaskLists
            currentTaskListName = currentTaskList.name
            currentTaskListTasks = currentTaskList.tasks

        // Filling the list title with info from the recieved object
        taskListTitle.text = currentTaskListName.toUpperCase()



        //Setting an adapter based on the set data
        recyclerViewTasks.layoutManager = LinearLayoutManager(myContext)
        adapterCurrent= CurrentListTasksAdapter(myContext,currentTaskListName)
        recyclerViewTasks.adapter = adapterCurrent

        //Checking if the Recycler View is empty
        checkIsEmpty()


        // Settin onClick Event to the button of the EditTextView (Creating and adding a task to the
        // chosen TaskList
        addTaskButton.setOnClickListener{
            currentTaskListTasks = dataManager.readCurrentTaskList(currentTaskListName)
            if(editTextNewTask.text.length<2) {
                Toast.makeText(myContext, "Too short name!", Toast.LENGTH_SHORT).show()

            }
            else if( editTextNewTask.text.toString() in currentTaskListTasks){
                Toast.makeText(myContext,"Task already exists!",Toast.LENGTH_SHORT).show()

            }
            else{
                //Checking for updates of current TaskList


                //Adding text from the EditView(EV) to the TaskList Array
                currentTaskListTasks.add(editTextNewTask.text.toString())

                // Creating a new instance of TaskList with new data
                val taskList = TaskList(currentTaskListName,currentTaskListTasks)

                // Saving new instance in Shared Preferences
                /*
                Shared Preferences contains only unique keys, so I will just rewrite an already
                existing pair (key -> TaskList name,value -> tasks) with some new data (value - > tasks)
                 */
                dataManager.saveListAndUpdate(taskList)

                // Updating my adapter and setting it to this activity, so new data will
                // be shown
                adapterCurrent= CurrentListTasksAdapter(myContext,currentTaskListName)
                recyclerViewTasks.adapter = adapterCurrent

                // Checking if the Recycler View is Empty. It won't be, so the Recycler is
                // visible now
                checkIsEmpty()
            }
        }

        return layout
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

    fun setCurrentTaskList(taskList: TaskList){
        currentTaskList = taskList
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
            outState.putParcelable(MainActivity.TASKLIST_KEY,currentTaskList)
    }


}