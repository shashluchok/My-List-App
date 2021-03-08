package com.example.android.mylistappinkotlin

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity(), ListDataManager.WorkOut {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))


        readAndSetLists()

    }



    override fun readAndSetLists() {

        val frag = ListSectionFragment.newInstance()
        supportFragmentManager.beginTransaction()
            .replace(R.id.fl_fragment_container,frag)
            .addToBackStack(null)
            .commit()
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


}