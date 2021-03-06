package com.example.android.mylistappinkotlin

import android.os.Bundle
import android.text.InputType
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private lateinit var myList:RecyclerView
    private val listDataManager = ListDataManager(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener {
            showCreateListDialog()
        }



        myList = findViewById<RecyclerView>(R.id.rv_my_recycler_view)
        myList.layoutManager = LinearLayoutManager(this)
        myList.adapter =ListSelectionRecyclerViewAdapter()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showCreateListDialog(){
        val dialogTitle = getString(R.string.name_of_list)
        val dialogConfirmation = getString(R.string.create_a_list)
        val editText = EditText(this)
        editText.inputType = InputType.TYPE_CLASS_TEXT
        editText.setHint(R.string.enter_your_list_name_here)
        val builder = AlertDialog.Builder(this).setTitle(dialogTitle).setView(editText)
        builder.setPositiveButton(dialogConfirmation){dialog,_->dialog.dismiss()}
        builder.create().show()
    }
}