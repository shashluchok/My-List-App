package com.example.android.mylistappinkotlin

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity(), ListDataManager.WorkOut,
    ListSelectionRecyclerViewAdapter.openCurrentTaskListListener, ListSelectionFragment.TaskListCreatedListener {

    private var currentTaskList: TaskList? = null

    private lateinit var listDataManager:ListDataManager


    companion object {
        const val TASKLIST_KEY = "TASK_LIST_KEY"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

        listDataManager = ListDataManager(this)
        if(savedInstanceState!=null){
            currentTaskList = savedInstanceState.getParcelable(TASKLIST_KEY)

        }
        else {
            if (findViewById<FrameLayout>(R.id.fl_fragment_container_main_current) != null) {
                showEmptyMessageFragment()
            }
        }
        readAndSetLists()


    }


    override fun readAndSetLists() {
        val frag = ListSelectionFragment.newInstance()
        supportFragmentManager.beginTransaction()
            .replace(R.id.fl_fragment_container, frag)
            .commit()
        updateCurrentTaskList()


    }


    private fun showEmptyMessageFragment() {
        val frag = EmptyTaskListFragment()
        supportFragmentManager.beginTransaction()
            .replace(R.id.fl_fragment_container_main_current, frag)
            .commit()
    }


    override fun openCurrentTaskList(list: TaskList) {
        when {
            (findViewById<FrameLayout>(R.id.fl_fragment_container_main_current) == null) -> {
                val intent = Intent(this, CurrentListItemActivity::class.java)
                intent.putExtra(CurrentListItemActivity.INTENT_LIST_KEY, list)
                ContextCompat.startActivity(this, intent, null)
            }
            (currentTaskList != list) -> {
                val frag = CurrentListItemFragment()
                frag.setCurrentTaskList(list)
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fl_fragment_container_main_current, frag)
                    .commit()

            }
        }
        currentTaskList = list
    }

    private fun updateCurrentTaskList(){
        if(currentTaskList!=null) {
            val frag = CurrentListItemFragment.newInstance()
            frag.setCurrentTaskList(currentTaskList as TaskList)
            supportFragmentManager.beginTransaction()
                .replace(R.id.fl_fragment_container_main_current, frag)
                .commit()
            val arr = arrayListOf<String>()
                for(cur in listDataManager.readLists()){
                    arr.add(cur.name)
                }
            if((currentTaskList as TaskList).name  !in arr){
                showEmptyMessageFragment()
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        if(currentTaskList!=null){
            outState.putParcelable(TASKLIST_KEY,currentTaskList)
        }
    }


    // --------------MENU------------------------
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




    // --------------MENU-OVER-------------------

    override fun onTaskListCreated(taskList: TaskList) {
        openCurrentTaskList(taskList)
    }

}