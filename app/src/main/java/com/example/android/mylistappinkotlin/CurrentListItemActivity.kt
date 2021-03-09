package com.example.android.mylistappinkotlin

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class CurrentListItemActivity : AppCompatActivity(), ListDataManager.WorkOut {

    companion object {
        const val INTENT_LIST_KEY = "INTENT_LIST_KEY"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_current_list_item)

        if(intent!=null) {
            readAndSetLists()
        }
        else{
            showEmptyMessage()
        }

    }


    override fun readAndSetLists() {

        val currentTaskList: TaskList =
            intent.getParcelableExtra<TaskList>(INTENT_LIST_KEY) as TaskList
        val currentListItemFragment = CurrentListItemFragment()
        currentListItemFragment.setCurrentTaskList(currentTaskList)
        supportFragmentManager.beginTransaction()
            .replace(R.id.fl_fragment_container_current_list, currentListItemFragment)
            .commit()

    }

    private fun showEmptyMessage(){

        val emptyTaskListFragment = EmptyTaskListFragment.newInstance()
        supportFragmentManager.beginTransaction()
            .replace(R.id.fl_fragment_container_current_list, emptyTaskListFragment)
            .commit()
    }

}