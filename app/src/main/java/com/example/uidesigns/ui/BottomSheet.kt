package com.example.uidesigns.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import com.example.uidesigns.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomSheet: BottomSheetDialogFragment() {

    private lateinit var listener:BottomSheetListener
    // Listener interface
    interface BottomSheetListener {
        fun onClickDist(buttonDist: Button)
        fun dateSel(buttonDate: EditText)
        fun saveBtn(buttonSave: Button)
    }

    fun attachListener(x: BottomSheetListener) {
        this.listener = x
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.bottom_drawer, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Setup your views and listeners here
        // For example, you can set up a click listener on a button:
        val buttonSave = view.findViewById<Button>(R.id.buttonSave)
        val buttonDist = view.findViewById<Button>(R.id.spinner_drawer)
        val buttonDate = view.findViewById<EditText>(R.id.date_selection)

        buttonSave.setOnClickListener {
            // Handle button click event
            listener.saveBtn(buttonSave)
            dismiss() // Dismiss the dialog
        }

        buttonDist.setOnClickListener {
            listener.onClickDist(buttonDist)
        }

        buttonDate.setOnClickListener{
            listener.dateSel(buttonDate)
        }
    }

    companion object {
        fun newInstance(): BottomSheet = BottomSheet()
    }



}