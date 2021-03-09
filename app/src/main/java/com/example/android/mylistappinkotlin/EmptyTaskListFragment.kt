package com.example.android.mylistappinkotlin

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class EmptyTaskListFragment : Fragment() {

    private lateinit var layout:View
    private lateinit var myContext:Context

    override fun onAttach(context: Context) {
        super.onAttach(context)
        myContext = context
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        layout = inflater.inflate(R.layout.fragment_empty_task_list, container, false)
        return layout
    }

    companion object {

        fun newInstance():EmptyTaskListFragment{
            return EmptyTaskListFragment()
        }
    }
}