package com.example.uidesigns.fragments

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.children
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.uidesigns.R
import com.example.uidesigns.adapter.RecyclerViewAdapterCalendar
import com.example.uidesigns.databinding.CalendarDayLayoutBinding
import com.example.uidesigns.model.TaskModel
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.DayPosition
import com.kizitonwose.calendar.core.daysOfWeek
import com.kizitonwose.calendar.core.firstDayOfWeekFromLocale
import com.kizitonwose.calendar.view.MonthDayBinder
import com.kizitonwose.calendar.view.ViewContainer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.time.temporal.ChronoUnit
import java.time.temporal.TemporalField
import java.time.temporal.WeekFields
import java.util.*

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
    private var selectedDate: LocalDate? = null

    private val today = LocalDate.now()
    private val fieldUS: TemporalField = WeekFields.of(Locale.US).dayOfWeek()
    private val startDay = today.with(fieldUS, 1).minusDays(1)
    private val lastDay = today.with(fieldUS, 7).plusDays(1)
    private val titleFormatter = DateTimeFormatter.ofPattern("MMMM yyyy", Locale.US)

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
        val calendarView = view.findViewById<com.kizitonwose.calendar.view.CalendarView>(R.id.calendarView)

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

        selectedDate = LocalDate.now()

        //start
        val minusMonth = if (startDay.monthValue == YearMonth.now().monthValue) 0 else 1
        val plusMonth = if (lastDay.monthValue == YearMonth.now().monthValue) 0 else 1
        val daysOfWeek = daysOfWeek(firstDayOfWeek = DayOfWeek.SUNDAY)

        val currentMonth = YearMonth.now()
        calendarView.apply {
            setup(currentMonth.minusMonths(minusMonth.toLong()), currentMonth.plusMonths(plusMonth.toLong()), daysOfWeek.first())
            scrollToMonth(currentMonth)
        }

        class DayViewContainer(view: View) : ViewContainer(view) {
            lateinit var day: CalendarDay // Will be set when this container is bound.
            val textView = CalendarDayLayoutBinding.bind(view).calendarDayText

            init {
                view.setOnClickListener {
                    // Check the day position as we do not want to select in or out dates.
                    // Adjust as requires to which date to be clickable
                    if (day.date == LocalDate.now() || day.date.until(LocalDate.now(), ChronoUnit.DAYS) in 0..6) {
                        // Keep a reference to any previous selection
                        // in case we overwrite it and need to reload it.
                        val currentSelection = selectedDate
                        if (currentSelection == day.date) {
                            // If the user clicks the same date, clear selection.
                            selectedDate = null
                            // Reload this date so the dayBinder is called
                            // and we can REMOVE the selection background.
                            calendarView.notifyDateChanged(currentSelection)
                        } else {
                            selectedDate = day.date
                            // Reload the newly selected date so the dayBinder is
                            // called and we can ADD the selection background.
                            calendarView.notifyDateChanged(day.date)
                            if (currentSelection != null) {
                                // We need to also reload the previously selected
                                // date so we can REMOVE the selection background.
                                calendarView.notifyDateChanged(currentSelection)
                            }
                        }
                    }
                }
            }
        }

        //When scroll change title set month
        calendarView.monthScrollListener = {
            curMonth.text = titleFormatter.format(it.yearMonth)
        }

        //setting months limit
        val startMonth = currentMonth.minusMonths(2)  // Adjust as needed
        val endMonth = currentMonth.plusMonths(5)  // Adjust as needed
        val firstDayOfWeek = firstDayOfWeekFromLocale() // Available from the library
        calendarView.setup(startMonth, endMonth, firstDayOfWeek)
        calendarView.scrollToMonth(currentMonth)

        //Setting title to short type Mon,Tue, etc...
        val titlesContainer = view.findViewById<ViewGroup>(R.id.titlesContainer)
        titlesContainer.children
            .map { it as TextView }
            .forEachIndexed { index, textView ->
                val dayOfWeek = daysOfWeek[index]
                val title = dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.getDefault())
                textView.text = title
            }

        //Color changing date for month
        calendarView.dayBinder = object : MonthDayBinder<DayViewContainer> {
            override fun create(view: View) = DayViewContainer(view)
            override fun bind(container: DayViewContainer, data: CalendarDay) {
                container.day = data
                val day = data
                container.textView.text = data.date.dayOfMonth.toString()

                //Changing the text to number date
                if (data.position == DayPosition.MonthDate) {
                    //if day selected change to highlight
                    if(day.date == selectedDate){
                        container.textView.setTextColor(Color.WHITE)
                        container.textView.setBackgroundResource(R.drawable.example_3_today_bg)
                    //if day is not selected no background
                    }else{
                        container.textView.background = null
                        //highlight color for required dates only
                        if(data.date == LocalDate.now() || data.date.until(LocalDate.now(), ChronoUnit.DAYS) in 0..6){
                            container.textView.setTextColor(Color.BLACK)
                        }else{
                            //other days greyed
                            container.textView.setTextColor(Color.GRAY)
                        }
                    }
                } else {
                    container.textView.visibility = View.INVISIBLE
                }
            }
        }

        // Inflate the layout for this fragment
        return view
    }

}