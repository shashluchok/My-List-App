package com.example.android.mylistappinkotlin

import android.content.Context
import androidx.preference.PreferenceManager

class ListDataManager(private val context: Context) {

    interface WorkOut {
        fun readAndSetLists()
    }


    private var listener:WorkOut = context as WorkOut

    fun saveList(list:TaskList){
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context).edit()
        sharedPreferences.putStringSet(list.name,list.tasks.toHashSet())
        sharedPreferences.apply()
    }
    fun readLists():ArrayList<TaskList> {
        val sharedPreferencesContents = PreferenceManager.getDefaultSharedPreferences(context).all
        val listOfTaskLists = ArrayList<TaskList>()
        for(tList in sharedPreferencesContents){
            val listHashSet = ArrayList(tList.value as HashSet<String>)
            val list = TaskList(tList.key,listHashSet)
            listOfTaskLists.add(list)
        }
        return listOfTaskLists
    }
    fun deleteList(key: String){
        val x = PreferenceManager.getDefaultSharedPreferences(context).edit()
        x.remove(key)
        x.apply()
        listener.readAndSetLists()

    }
}