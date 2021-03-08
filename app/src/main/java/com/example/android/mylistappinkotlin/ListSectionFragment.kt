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


class ListSectionFragment : Fragment(), ListDataManager.WorkOut {

    private lateinit var myList: RecyclerView
    private lateinit var listDataManager: ListDataManager
    private lateinit var myContext2: Context
    private lateinit var layout:View

    companion object {
        fun newInstance():ListSectionFragment{
            return ListSectionFragment()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

            myContext2 = context
            listDataManager = ListDataManager(context)


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

        readAndSetLists()
        return layout
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)



    }

    override fun onDetach() {
        super.onDetach()
    }

    interface OnListItemFragmentInteractionListener {
        fun onListItemSelected (list:TaskList)
    }



    override fun readAndSetLists() {
        myList.adapter = ListSelectionRecyclerViewAdapter(listDataManager.readLists(), myContext2)
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

            //Saving a list with the written title to Shared Preferences
            listDataManager.saveList(list)

            val recyclerAdapter = myList.adapter as ListSelectionRecyclerViewAdapter
            recyclerAdapter.addList(list)
            dialog.dismiss()

        }
        builder.create().show()

    }

}