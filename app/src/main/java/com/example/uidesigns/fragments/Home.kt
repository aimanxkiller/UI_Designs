package com.example.uidesigns.fragments

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.uidesigns.R
import com.example.uidesigns.adapter.AdapterListener
import com.example.uidesigns.adapter.RecyclerViewAdapter
import com.example.uidesigns.adapter.RecyclerViewAdapterCheckbox
import com.example.uidesigns.model.TaskList
import com.example.uidesigns.model.TaskModel
import com.example.uidesigns.ui.FillUpInspectionActivity
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.util.*
import java.util.Calendar

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class Home : Fragment(),AdapterListener {
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var recyclerViewAdapter:RecyclerViewAdapter
    private lateinit var recyclerViewAdapter2:RecyclerViewAdapterCheckbox
    private lateinit var dataParent:ArrayList<TaskList>
    private lateinit var distChoice:TextView
    private lateinit var dialogDistChoice:Dialog

    private lateinit var data:ArrayList<TaskModel>
    private lateinit var data2:ArrayList<TaskModel>
    private lateinit var editDate:EditText
    private lateinit var buttonDist:Button
    private lateinit var save:Button
    private var date:LocalDate?=null
    private lateinit var x:List<String>

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


        recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            recyclerViewAdapter = RecyclerViewAdapter(this@Home)
            adapter = recyclerViewAdapter
            recyclerViewAdapter.getData(dataParent)
        }

        //Button for setting new schedule
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

        //stuff in dialog drawer bottom
        editDate = dialog.findViewById(R.id.date_selection)
        buttonDist = dialog.findViewById(R.id.spinner_drawer)
        save = dialog.findViewById(R.id.buttonSave)

        //User inputs date here
        editDate.setOnClickListener {
            showDatePickerDialog()
        }

        x = listOf(
            "Distributor 1","Distributor 2","Distributor 3",
            "Distributor 4","Distributor 5","Distributor 6"
        )

        //open new dialogDrawer
        buttonDist.setOnClickListener {
            distributorDialog(x)
        }

        saveButton(dialog)

        dialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window?.attributes?.windowAnimations = R.style.DialogAnimation
        dialog.window?.setGravity(Gravity.BOTTOM)
        dialog.show()

    }

    //showing distributor list dialog selection
    private fun distributorDialog(x: List<String>) {
        dialogDistChoice = Dialog(requireContext())
        dialogDistChoice.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialogDistChoice.setContentView(R.layout.bottom_drawer_dist)
        dialogDistChoice.setCancelable(false)

        //things inside dialogDistChoice
        val recyclerView:RecyclerView = dialogDistChoice.findViewById(R.id.recycler_distributor)
        distChoice = dialogDistChoice.findViewById(R.id.selectionDist)

        recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            recyclerViewAdapter2 = RecyclerViewAdapterCheckbox(this@Home)
            adapter = recyclerViewAdapter2
            recyclerViewAdapter2.getData(x)
        }

        dialogDistChoice.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT)
        dialogDistChoice.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialogDistChoice.window?.attributes?.windowAnimations = R.style.DialogAnimation
        dialogDistChoice.window?.setGravity(Gravity.BOTTOM)
        dialogDistChoice.show()
    }

    //save settings
    private fun saveButton(dialog: Dialog) {

        save.setOnClickListener {

            //checking for stuff
            if(editDate.text.isNullOrEmpty()){
                Toast.makeText(requireContext(),"Please fill up date & select a distributor",Toast.LENGTH_SHORT).show()
            }else{
                confirmationDialog(dialog)
            }
        }
    }

    //confirmDialog
    @SuppressLint("InflateParams")
    private fun confirmationDialog(dialogParent: Dialog) {

        //show confirmation dialog
        val dialogBinding = layoutInflater.inflate(R.layout.custom_dialog,null)
        val myDialog = Dialog(requireContext())
        myDialog.setContentView(dialogBinding)

        myDialog.setCancelable(false)
        myDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        myDialog.show()

        //stuff in dialog yes and no
        val cancel:Button = myDialog.findViewById(R.id.buttonDialogNo)
        val yes:Button = myDialog.findViewById(R.id.buttonDialogYes)

        cancel.setOnClickListener {
            myDialog.dismiss()
        }
        yes.setOnClickListener {
            parseDate()
            Log.e("Test2",date.toString())
            myDialog.dismiss()
            dialogParent.dismiss()
            Toast.makeText(requireContext(),"Saved Task",Toast.LENGTH_SHORT).show()
        }
    }

    //parsing date?
    private fun parseDate(){
        //Getting date here
        val dateFormatter = DateTimeFormatter.ofPattern("MMMM dd, yyyy")
        val dateText = editDate.text.toString()

        try {
            date= LocalDate.parse(dateText,dateFormatter)
        }catch (e:DateTimeParseException){
            Log.e("ErrorParse", e.toString())
        }
    }

    //Showing date picker calendar popup
    private fun showDatePickerDialog(){
        val currentDate = Calendar.getInstance()
        val year = currentDate.get(Calendar.YEAR)
        val month = currentDate.get(Calendar.MONTH)
        val day = currentDate.get(Calendar.DAY_OF_MONTH)

        // create a new DatePickerDialog instance
        val datePickerDialog = DatePickerDialog(requireContext(), { _, year, month, dayOfMonth ->
            // set the selected date value in the EditText
            val selectedDate = "$year-${month + 1}-$dayOfMonth"

            val inputDateFormat = SimpleDateFormat("yyyy-M-d", Locale.US)
            val inputDate = inputDateFormat.parse(selectedDate)

            val outputDateFormat = SimpleDateFormat("MMMM dd, yyyy",Locale.US)
            editDate.setText(outputDateFormat.format(inputDate))
        }, year, month, day)

        // show the DatePickerDialog
        datePickerDialog.show()
    }

    override fun onClicked(data:TaskModel) {
        //set to go to Fill Up Inspection Activity
        val intent = Intent(requireActivity(),FillUpInspectionActivity::class.java)
        intent.putExtra("Name",data.task_title)
        startActivity(intent)
    }

    @SuppressLint("SetTextI18n")
    override fun onCheck(choices: MutableList<String>?) {
        if (choices==null){
            buttonDist.text = "Select Distributor"
            distChoice.text = "Select Distributor"
        }else{
            buttonDist.text = choices.toString().replace("[","").replace("]","")
            distChoice.text = choices.toString().replace("[","").replace("]","")
        }

    }

    override fun distDone() {
        //closing dist
        dialogDistChoice.dismiss()
    }

}