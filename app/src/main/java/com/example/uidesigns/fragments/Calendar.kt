package com.example.uidesigns.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.uidesigns.R
import com.example.uidesigns.adapter.RecyclerViewAdapterCalendar
import com.example.uidesigns.model.TaskModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import java.util.Calendar

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class Calendar : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var data:ArrayList<TaskModel>
    private lateinit var recyclerViewAdapter: RecyclerViewAdapterCalendar

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
        val view = inflater.inflate(R.layout.fragment_calendar, container, false)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_calendar)
        val curMonth = view.findViewById<TextView>(R.id.textCurrentMonth)
        val calendarView = view.findViewById<CalendarView>(R.id.calendarView)

        setCurrentMonth(curMonth)

        data = arrayListOf(
            TaskModel("Task 1",3),
            TaskModel("Task 2",2),
            TaskModel("Task 3",1),
            TaskModel("Task 10",1),
            TaskModel("Task 11",1),
        )

        lifecycleScope.launch(Dispatchers.IO){
            recyclerView.apply {
                layoutManager = LinearLayoutManager(requireContext())
                recyclerViewAdapter = RecyclerViewAdapterCalendar()
                adapter = recyclerViewAdapter
                recyclerViewAdapter.getData(data)
            }
        }

        // Inflate the layout for this fragment
        return view
    }

    private fun setCurrentMonth(curMonth: TextView) {
        val currentDate = Calendar.getInstance()
        val year = currentDate.get(Calendar.YEAR)
        val month = currentDate.get(Calendar.MONTH)
        val x = "$month $year"

        val inputDateFormat = SimpleDateFormat("M yyyy", Locale.US)
        val outputDateFormat = SimpleDateFormat("MMMM yyyy",Locale.US)

        curMonth.text = outputDateFormat.format(inputDateFormat.parse(x))
    }

}