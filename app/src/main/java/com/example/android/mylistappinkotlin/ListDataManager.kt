package com.example.android.mylistappinkotlin

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager

class ListDataManager(private val context: Context) {
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
    fun deleteList(){
        val x = PreferenceManager.getDefaultSharedPreferences(context).edit()

    }
}