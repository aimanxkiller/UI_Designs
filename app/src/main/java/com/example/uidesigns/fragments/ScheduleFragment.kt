//package sa.goldenbrown.qa.app.ui.schedule
//
//import android.os.Bundle
//import android.view.View
//import android.widget.TextView
//import androidx.core.view.children
//import androidx.fragment.app.viewModels
//import androidx.lifecycle.MutableLiveData
//import com.kizitonwose.calendarview.model.CalendarDay
//import com.kizitonwose.calendarview.model.CalendarMonth
//import com.kizitonwose.calendarview.model.DayOwner
//import com.kizitonwose.calendarview.ui.DayBinder
//import com.kizitonwose.calendarview.ui.MonthHeaderFooterBinder
//import com.kizitonwose.calendarview.ui.ViewContainer
//import com.kizitonwose.calendarview.utils.Size
//import dagger.hilt.android.AndroidEntryPoint
//import sa.goldenbrown.qa.app.R
//import sa.goldenbrown.qa.app.base.BaseFragment
//import sa.goldenbrown.qa.app.base.BaseViewModel
//import sa.goldenbrown.qa.app.data.model.response.inspections.InspectionsItem
//import sa.goldenbrown.qa.app.data.model.response.inspections.InspectionsResponse
//import sa.goldenbrown.qa.app.databinding.Example3CalendarDayBinding
//import sa.goldenbrown.qa.app.databinding.Example3CalendarHeaderBinding
//import sa.goldenbrown.qa.app.databinding.FragmentScheduleBinding
//import sa.goldenbrown.qa.app.ui.home.adapter.HomeAdapter
//import sa.goldenbrown.qa.app.utils.*
//import sa.goldenbrown.qa.app.utils.setTextColorRes
//import java.time.LocalDate
//import java.time.YearMonth
//import java.time.format.DateTimeFormatter
//import java.time.temporal.TemporalField
//import java.time.temporal.WeekFields
//import java.util.*
//import kotlin.collections.ArrayList
//
//@AndroidEntryPoint
//class ScheduleFragment : BaseFragment(R.layout.fragment_schedule) {
//
//    private lateinit var binding: FragmentScheduleBinding
//    private val viewModel: ScheduleViewModel by viewModels()
//    private var selectedDate: LocalDate? = null
//    private val today = LocalDate.now()
//    private val fieldUS: TemporalField = WeekFields.of(Locale.US).dayOfWeek()
//    private val startDay = today.with(fieldUS, 1).minusDays(1)
//    private val lastDay = today.with(fieldUS, 7).plusDays(1)
//    private val titleFormatter = DateTimeFormatter.ofPattern("MMMM yyyy", Locale.US)
//    private val serverFormatter by lazy { DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.US) }
//    private val noInspectionFormatter by lazy { DateTimeFormatter.ofPattern("dd MMMM yyyy", Locale.US) }
//    private val inspectionLiveData = MutableLiveData<List<InspectionsItem>>()
//    private val inspectionList = ArrayList<InspectionsItem>()
//
//    override fun getViewModel(): BaseViewModel = viewModel
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        binding = FragmentScheduleBinding.bind(view)
//        inspectionLiveData.observe(viewLifecycleOwner, {
//            if (it.isEmpty()) {
//                binding.noInspection.makeVisible()
//            } else {
//                binding.noInspection.makeGone()
//            }
//            val adapter = HomeAdapter(it, null)
//            binding.scheduleInspectionList.adapter = adapter
//        })
//
//        val minusMonth = if (startDay.monthValue == YearMonth.now().monthValue) 0 else 1
//        val plusMonth = if (lastDay.monthValue == YearMonth.now().monthValue) 0 else 1
//
//        val daysOfWeek = daysOfWeekFromLocale()
//
//        val currentMonth = YearMonth.now()
//        binding.exThreeCalendar.apply {
//            setup(currentMonth.minusMonths(minusMonth.toLong()), currentMonth.plusMonths(plusMonth.toLong()), daysOfWeek.first())
//            scrollToMonth(currentMonth)
//        }
//
//        if (savedInstanceState == null) {
//            binding.exThreeCalendar.post {
//                // Show today's events initially.
//                selectDate(today)
//            }
//        }
//
//        class DayViewContainer(view: View) : ViewContainer(view) {
//            lateinit var day: CalendarDay // Will be set when this container is bound.
//            val binding = Example3CalendarDayBinding.bind(view)
//
//            init {
//                view.setOnClickListener {
//                    if (day.owner == DayOwner.THIS_MONTH && (day.date.isAfter(startDay) && day.date.isBefore(lastDay))) {
//                        selectDate(day.date)
//                        inspectionLiveData.value = inspectionList.filter { it.dateInitial == serverFormatter.format(day.date) }
//                    }
//                }
//            }
//        }
//
//        binding.exThreeCalendar.daySize = Size(Int.MIN_VALUE, 110)
//
//        binding.exThreeCalendar.dayBinder = object : DayBinder<DayViewContainer> {
//            override fun create(view: View) = DayViewContainer(view)
//            override fun bind(container: DayViewContainer, day: CalendarDay) {
//                container.day = day
//                val textView = container.binding.exThreeDayText
//
//                textView.text = day.date.dayOfMonth.toString()
//
//                if (day.owner == DayOwner.THIS_MONTH) {
//                    textView.makeVisible()
//                    when (day.date) {
//                        today -> {
//                            if (today == selectedDate) {
//                                textView.setTextColorRes(R.color.white)
//                                textView.setBackgroundResource(R.drawable.example_3_today_bg)
//                            } else {
//                                textView.setTextColorRes(R.color.colorAccent)
//                                textView.background = null
//                            }
//                        }
//                        selectedDate -> {
//                            textView.setTextColorRes(R.color.white)
//                            textView.setBackgroundResource(R.drawable.example_3_today_bg)
//                        }
//                        else -> {
//                            if (day.date.isAfter(startDay) && day.date.isBefore(lastDay)) {
//                                textView.setTextColorRes(R.color.black)
//                                textView.background = null
//                            } else {
//                                textView.setTextColorRes(R.color.example_4_grey_past)
//                                textView.background = null
//                            }
//
//                        }
//                    }
//                } else {
//                    textView.makeInVisible()
//                }
//            }
//        }
//
//        binding.exThreeCalendar.monthScrollListener = {
//            binding.titleText.text = titleFormatter.format(it.yearMonth)
//        }
//
//        class MonthViewContainer(view: View) : ViewContainer(view) {
//            val legendLayout = Example3CalendarHeaderBinding.bind(view).legendLayout.legendLayout
//        }
//
//        binding.exThreeCalendar.monthHeaderBinder = object : MonthHeaderFooterBinder<MonthViewContainer> {
//            override fun create(view: View) = MonthViewContainer(view)
//            override fun bind(container: MonthViewContainer, month: CalendarMonth) {
//                // Setup each header day text if we have not done that already.
//                if (container.legendLayout.tag == null) {
//                    container.legendLayout.tag = month.yearMonth
//                    container.legendLayout.children.map { it as TextView }.forEachIndexed { index, tv ->
//                        tv.text = daysOfWeek[index].name.first().toString()
//                        tv.setTextColorRes(R.color.black)
//                    }
//                }
//            }
//        }
//
//        viewModel.onRequestInspectionList(serverFormatter.format(startDay.plusDays(1)) + "~" + serverFormatter.format(lastDay.minusDays(1)))
//    }
//
//    private fun selectDate(date: LocalDate) {
//        if (selectedDate != date) {
//            val oldDate = selectedDate
//            selectedDate = date
//            oldDate?.let { binding.exThreeCalendar.notifyDateChanged(it) }
//            binding.exThreeCalendar.notifyDateChanged(date)
//            binding.noInspection.text = getString(R.string.no_inspection)+" "+noInspectionFormatter.format(selectedDate)
//        }
//    }
//
//    override fun onSuccess(result: Result.Success<Any>) {
//        if (result.data is InspectionsResponse) {
//            inspectionList.addAll(result.data.inspections)
//            inspectionLiveData.value = inspectionList.filter { it.dateInitial == serverFormatter.format(selectedDate) }
//        }
//    }
//
//
//}