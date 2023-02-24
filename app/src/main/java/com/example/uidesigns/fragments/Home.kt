package com.example.uidesigns.fragments

import android.app.DatePickerDialog
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import java.util.Calendar
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
    private lateinit var editDate:EditText
    private lateinit var spinner:Spinner

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

        val bottomSheet:Button = view.findViewById(R.id.buttonSetNewSchedule)

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

        bottomSheet.setOnClickListener {
            showDialog()
        }

        // Inflate the layout for this fragment
        return view
    }

    private fun showDialog(){
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.bottom_drawer)

        editDate = dialog.findViewById<EditText>(R.id.date_selection)
        spinner = dialog.findViewById(R.id.spinner_drawer)

        editDate.setOnClickListener {
            showDatePickerDialog()
        }

        val x:List<String> = listOf(
            "Distributor 1","Distributor 2","Distributor 3",
            "Distributor 4","Distributor 5","Distributor 6"
        )
        val arrayAdapter=ArrayAdapter(requireContext(),android.R.layout.simple_spinner_dropdown_item,x)

        spinner.adapter=arrayAdapter
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                //place selection here
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
                //Do nothings
            }
        }

        dialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window?.attributes?.windowAnimations = R.style.DialogAnimation
        dialog.window?.setGravity(Gravity.BOTTOM)

        dialog.show()
    }

    private fun showDatePickerDialog(){
        val currentDate = Calendar.getInstance()
        val year = currentDate.get(Calendar.YEAR)
        val month = currentDate.get(Calendar.MONTH)
        val day = currentDate.get(Calendar.DAY_OF_MONTH)

        // create a new DatePickerDialog instance
        val datePickerDialog = DatePickerDialog(requireContext(), { _, year, month, dayOfMonth ->
            // set the selected date value in the EditText
            val selectedDate = "$year-${month + 1}-$dayOfMonth"
            editDate.setText(selectedDate)
        }, year, month, day)

        // show the DatePickerDialog
        datePickerDialog.show()

    }

}