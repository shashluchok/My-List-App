package com.example.android.mylistappinkotlin

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.InputFilter
import android.text.InputType
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.Filter
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity(), ListDataManager.WorkOut {

    private lateinit var myList: RecyclerView
    private val listDataManager = ListDataManager(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener {
            showCreateListDialog()
        }

        //Setting a Recycler View to show User's lists
        myList = findViewById(R.id.rv_my_recycler_view)
        myList.layoutManager = LinearLayoutManager(this)
        readAndSetLists()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }


    // Overriding a method from ListDataManager.WorkOut interface, so it (ListDataManager) can now
    // call this method from its body
    override fun readAndSetLists() {
        myList.adapter = ListSelectionRecyclerViewAdapter(listDataManager.readLists(), this)
    }

    private fun showCreateListDialog() {
        val dialogTitle = getString(R.string.name_of_list)
        val dialogConfirmation = getString(R.string.create_a_list)
        val editText = EditText(this)
        editText.inputType = InputType.TYPE_CLASS_TEXT
        editText.setHint(R.string.enter_your_list_name_here)

        //Setting max length of a title User can type in the EditTextView
        val filterArray = arrayOf(InputFilter.LengthFilter(10) as InputFilter)
        editText.filters = filterArray

        val builder = AlertDialog.Builder(this).setTitle(dialogTitle).setView(editText)
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