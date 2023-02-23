package com.example.uidesigns.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.uidesigns.R
import com.example.uidesigns.adapter.RecyclerViewNested
import com.example.uidesigns.model.TaskList
import com.example.uidesigns.model.TaskModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class Home : Fragment() {
    private var param1: String? = null
    private var param2: String? = null
    lateinit var recyclerViewAdapter:RecyclerViewNested
    lateinit var dataParent:ArrayList<TaskList>
    private lateinit var data:ArrayList<TaskModel>
    private lateinit var data2:ArrayList<TaskModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_home, container, false)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_home)


        //Test Data
        data = arrayListOf(
            TaskModel("Task 1",3),
            TaskModel("Task 2",2),
            TaskModel("Task 3",1),
            TaskModel("Task 10",1),
            TaskModel("Task 11",1),
        )
        data2 = arrayListOf(
            TaskModel("Task 4",3),
            TaskModel("Task 5",2),
            TaskModel("Task 6",3),
            TaskModel("Task 7",3),
            TaskModel("Task 8",1),
            TaskModel("Task 9",2),
        )
        data.add(data2[0])
        data2.removeAt(0)

        dataParent = arrayListOf(
            TaskList(1,data),
            TaskList(2,data2)
        )

        lifecycleScope.launch(Dispatchers.IO){
            recyclerView.apply {
                layoutManager = LinearLayoutManager(requireContext())
                recyclerViewAdapter = RecyclerViewNested()
                adapter = recyclerViewAdapter
                recyclerViewAdapter.getData(dataParent)
            }
        }

        // Inflate the layout for this fragment
        return view
    }

}