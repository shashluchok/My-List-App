package com.example.android.mylistappinkotlin

import android.content.Context
import androidx.preference.PreferenceManager

class ListDataManager(private val context: Context) {

    interface WorkOut {
        fun readAndSetLists()
    }


    private var listener: WorkOut = context as WorkOut

    fun saveListAndUpdate(list: TaskList) {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context).edit()
        sharedPreferences.putStringSet(list.name, list.tasks.toHashSet())
        sharedPreferences.apply()
        listener.readAndSetLists()
    }

    fun readLists(): ArrayList<TaskList> {
        val sharedPreferencesContents = PreferenceManager.getDefaultSharedPreferences(context).all
        val listOfTaskLists = ArrayList<TaskList>()
        for (tList in sharedPreferencesContents) {
            val listHashSet = ArrayList(tList.value as HashSet<String>)
            val list = TaskList(tList.key, listHashSet)
            listOfTaskLists.add(list)
        }
        return listOfTaskLists
    }

    fun deleteList(key: String) {
        val x = PreferenceManager.getDefaultSharedPreferences(context).edit()
        x.remove(key)
        x.apply()
        listener.readAndSetLists()

    }

    fun readCurrentTaskList(name: String): ArrayList<String> {
        val sharedPreferencesContents = PreferenceManager.getDefaultSharedPreferences(context).all
        var listOfTasks = arrayListOf<String>()
        for (tList in sharedPreferencesContents) {
            if (tList.key.toString() == name) {
                listOfTasks = ArrayList(tList.value!! as HashSet<String>)
            }

        }
        return listOfTasks
    }

    fun deleteCurrentTask(name: String, item: String) {
        val arr = readCurrentTaskList(name)
        arr.remove(item)
        val x = PreferenceManager.getDefaultSharedPreferences(context).edit()
        x.remove(name)
        x.apply()
        val list = TaskList(name, arr)
        saveListAndUpdate(list)
    }
}