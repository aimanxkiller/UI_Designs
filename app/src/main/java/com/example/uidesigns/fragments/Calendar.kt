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
import com.example.uidesigns.databinding.CalendarDayLayoutBinding
import com.example.uidesigns.databinding.FragmentCalendarBinding
import com.example.uidesigns.model.TaskModel
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.firstDayOfWeekFromLocale
import com.kizitonwose.calendar.view.MonthDayBinder
import com.kizitonwose.calendar.view.ViewContainer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.time.temporal.TemporalField
import java.time.temporal.WeekFields
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
    private lateinit var binding:FragmentCalendarBinding

    private var selectedDate: LocalDate? = null
    private val today = LocalDate.now()
    private val fieldUS: TemporalField = WeekFields.of(Locale.US).dayOfWeek()
    private val startDay = today.with(fieldUS, 1).minusDays(1)
    private val lastDay = today.with(fieldUS, 7).plusDays(1)
    private val titleFormatter = DateTimeFormatter.ofPattern("MMMM yyyy", Locale.US)
    private val serverFormatter by lazy { DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.US) }
    private val noInspectionFormatter by lazy { DateTimeFormatter.ofPattern("dd MMMM yyyy", Locale.US) }

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
        binding = FragmentCalendarBinding.inflate(inflater, container, false)

        val recyclerView = binding.recyclerCalendar
        val curMonth = binding.textCurrentMonth
        val calendarView = binding.calendarView

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


        //start
        val minusMonth = if (startDay.monthValue == YearMonth.now().monthValue) 0 else 1
        val plusMonth = if (lastDay.monthValue == YearMonth.now().monthValue) 0 else 1

        val daysOfWeek = daysOfWeekFromLocale()

        val currentMonth = YearMonth.now()
        calendarView.apply {
            setup(currentMonth.minusMonths(minusMonth.toLong()), currentMonth.plusMonths(plusMonth.toLong()), daysOfWeek.first())
            scrollToMonth(currentMonth)
        }

        class DayViewContainer(view: View) : ViewContainer(view) {
            val textView = CalendarDayLayoutBinding.bind(view).calendarDayText
        }



        calendarView.dayBinder = object : MonthDayBinder<DayViewContainer> {
            // Called only when a new container is needed.
            override fun create(view: View) = DayViewContainer(view)

            // Called every time we need to reuse a container.
            override fun bind(container: DayViewContainer, data: CalendarDay) {
                container.textView.text = data.date.dayOfMonth.toString()
            }
        }

        
        val startMonth = currentMonth.minusMonths(100)  // Adjust as needed
        val endMonth = currentMonth.plusMonths(100)  // Adjust as needed
        val firstDayOfWeek = firstDayOfWeekFromLocale() // Available from the library
        calendarView.setup(startMonth, endMonth, firstDayOfWeek)
        calendarView.scrollToMonth(currentMonth)

        // Inflate the layout for this fragment
        return binding.root
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

    fun daysOfWeekFromLocale(): Array<DayOfWeek> {
        val firstDayOfWeek = WeekFields.of(Locale.getDefault()).firstDayOfWeek
        var daysOfWeek = DayOfWeek.values()
        // Order `daysOfWeek` array so that firstDayOfWeek is at index 0.
        // Only necessary if firstDayOfWeek != DayOfWeek.MONDAY which has ordinal 0.
        if (firstDayOfWeek != DayOfWeek.MONDAY) {
            val rhs = daysOfWeek.sliceArray(firstDayOfWeek.ordinal..daysOfWeek.indices.last)
            val lhs = daysOfWeek.sliceArray(0 until firstDayOfWeek.ordinal)
            daysOfWeek = rhs + lhs
        }
        return daysOfWeek
    }

}