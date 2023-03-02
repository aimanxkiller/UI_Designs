package com.example.uidesigns.ui

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.res.Resources
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.uidesigns.R
import com.example.uidesigns.databinding.ActivityFillUpInspectionBinding

class FillUpInspectionActivity : AppCompatActivity() {

    private lateinit var binding:ActivityFillUpInspectionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFillUpInspectionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.TitlePlaceholder.text = intent.getStringExtra("Name")

        binding.inspectionBackIcon.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        binding.fillUpSaveBtn.setOnClickListener {
            confirmationDialog()
        }

    }

    //Placeholder just for layout
    @SuppressLint("InflateParams", "SetTextI18n")
    private fun confirmationDialog(){

        val dialogBinding = layoutInflater.inflate(R.layout.custom_dialog,null)
        val myDialog = Dialog(this)

        myDialog.setContentView(dialogBinding)

        myDialog.setCancelable(false)
        myDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        //custom margin
        myDialog.window?.let { window ->
            val layoutParams = window.attributes.apply {
                // Set the width to match parent and add a horizontal margin of 20dp
                val width = Resources.getSystem().displayMetrics.widthPixels
                val margin = resources.getDimensionPixelSize(R.dimen.horizontal_padding)
                this.width = width - (4 * margin)
            }

            window.attributes = layoutParams
        }


        val title: TextView = myDialog.findViewById(R.id.dialog_title_custom)
        val content: TextView = myDialog.findViewById(R.id.dialog_content_custom)

        title.text = "Do you want to Save & Submit"
        content.visibility = View.GONE

        //stuff in dialog yes and no
        val cancel: Button = myDialog.findViewById(R.id.buttonDialogNo)
        val yes: Button = myDialog.findViewById(R.id.buttonDialogYes)

        cancel.setOnClickListener {
            Toast.makeText(this,"Pressed No",Toast.LENGTH_SHORT).show()
            myDialog.dismiss()
        }
        yes.setOnClickListener {
            Toast.makeText(this,"Pressed Yes",Toast.LENGTH_SHORT).show()
        }

        //inflate/show dialog
        myDialog.show()
    }
}