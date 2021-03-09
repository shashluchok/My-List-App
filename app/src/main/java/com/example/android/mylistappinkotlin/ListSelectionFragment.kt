package com.example.android.mylistappinkotlin

import android.content.Context
import android.os.Bundle
import android.text.InputFilter
import android.text.InputType
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton


class ListSelectionFragment : Fragment() {

    private lateinit var myList: RecyclerView
    private lateinit var listDataManager: ListDataManager
    private lateinit var myContext2: Context
    private lateinit var layout: View
    private lateinit var taskListCreatedListener: TaskListCreatedListener

    interface TaskListCreatedListener {
        fun onTaskListCreated(taskList:TaskList)
    }

    companion object {
        fun newInstance(): ListSelectionFragment {
            return ListSelectionFragment()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        myContext2 = context
        listDataManager = ListDataManager(context)
        taskListCreatedListener = context as TaskListCreatedListener

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        layout = inflater.inflate(R.layout.fragment_list_section, container, false)

        layout.findViewById<FloatingActionButton>(R.id.fab).setOnClickListener {
            showCreateListDialog()
        }



        myList = layout.findViewById(R.id.rv_my_recycler_view)
        myList.layoutManager = LinearLayoutManager(myContext2)
        myList.adapter = ListSelectionRecyclerViewAdapter(listDataManager.readLists(), myContext2)
        return layout
    }


    private fun showCreateListDialog() {
        val dialogTitle = getString(R.string.name_of_list)
        val dialogConfirmation = getString(R.string.create_a_list)
        val editText = EditText(myContext2)
        editText.inputType = InputType.TYPE_CLASS_TEXT
        editText.setHint(R.string.enter_your_list_name_here)

        //Setting max length of a title User can type in the EditTextView
        val filterArray = arrayOf(InputFilter.LengthFilter(10) as InputFilter)
        editText.filters = filterArray

        val builder = AlertDialog.Builder(myContext2).setTitle(dialogTitle).setView(editText)
        builder.setPositiveButton(dialogConfirmation) { dialog, _ ->
            val list = TaskList(editText.text.toString())

            taskListCreatedListener.onTaskListCreated(list)

            //Saving a list with the written title to Shared Preferences
            listDataManager.saveListAndUpdate(list)

            dialog.dismiss()

        }
        builder.create().show()

    }

}