package com.example.uidesigns.ui

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.content.res.Resources
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.provider.OpenableColumns
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.uidesigns.R
import com.example.uidesigns.databinding.ActivityFillUpInspectionBinding
import java.io.File

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

        setupUpload()
        setupCancel()

    }

    //Cancel upload
    private fun setupCancel(){
        binding.uploadCancel1.setOnClickListener {
            binding.uploadName1.text = null
            binding.uploadName1.visibility = View.GONE
            binding.uploadCancel1.visibility = View.GONE
            binding.uploadIcon1.setImageResource(R.drawable.icon_upload)
        }
        binding.uploadCancel2.setOnClickListener {
            binding.uploadName2.text = null
            binding.uploadName2.visibility = View.GONE
            binding.uploadCancel2.visibility = View.GONE
            binding.uploadIcon2.setImageResource(R.drawable.icon_upload)
        }
        binding.uploadCancel3.setOnClickListener {
            binding.uploadName3.text = null
            binding.uploadName3.visibility = View.GONE
            binding.uploadCancel3.visibility = View.GONE
            binding.uploadIcon3.setImageResource(R.drawable.icon_upload)
        }
    }

    private fun setupUpload() {
        val options = arrayOf<CharSequence>("Take Photo", "Choose from Gallery", "Cancel")
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Choose your profile picture")

        binding.uploadIcon1.setOnClickListener {
            builder.setItems(options) { dialog, item ->
                when {
                    options[item] == "Take Photo" -> {
                        //take pic here
                    }
                    options[item] == "Choose from Gallery" -> {
                        pickImage1.launch("image/*")
                    }
                    options[item] == "Cancel" -> {
                        dialog.dismiss()
                    }
                }
            }
            builder.show()
        }
        binding.uploadIcon2.setOnClickListener {
            pickImage2.launch("image/*")
        }
        binding.uploadIcon3.setOnClickListener {
            pickImage3.launch("image/*")
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

    // Create the activity result launcher
    private val pickImage1 = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        if (uri != null) {
            // Get the image bitmap from the URI
            val inputStream = contentResolver.openInputStream(uri)
            val bitmap = BitmapFactory.decodeStream(inputStream)

            //Set and get name from file
            val name = getFileName(uri)

            //change the icon etc
            binding.uploadIcon1.setImageResource(R.drawable.icon_upload_red)
            binding.uploadName1.visibility = View.VISIBLE
            binding.uploadName1.text = name
            binding.uploadCancel1.visibility = View.VISIBLE
        }else{
            //do nothing
        }
    }
    private val pickImage2 = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        if (uri != null) {
            // Get the image bitmap from the URI
            val inputStream = contentResolver.openInputStream(uri)
            val bitmap = BitmapFactory.decodeStream(inputStream)

            //Set and get name from file
            val name = getFileName(uri)

            //change the icon etc
            binding.uploadIcon2.setImageResource(R.drawable.icon_upload_red)
            binding.uploadName2.visibility = View.VISIBLE
            binding.uploadName2.text = name
            binding.uploadCancel2.visibility = View.VISIBLE
        }else{
            //do nothing
        }
    }
    private val pickImage3 = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        if (uri != null) {
            // Get the image bitmap from the URI
            val inputStream = contentResolver.openInputStream(uri)
            val bitmap = BitmapFactory.decodeStream(inputStream)

            //Set and get name from file
            val name = getFileName(uri)

            //change the icon etc
            binding.uploadIcon3.setImageResource(R.drawable.icon_upload_red)
            binding.uploadName3.visibility = View.VISIBLE
            binding.uploadName3.text = name
            binding.uploadCancel3.visibility = View.VISIBLE
        }else{
            //do nothing
        }
    }

    //imageCapture

    //get Filename func
    private fun getFileName(uri: Uri): String? {
        // Get the file name from the URI
        var name: String? = null
        val cursor = contentResolver.query(uri, null, null, null, null)
        cursor?.use {
            if (it.moveToFirst()) {
                val fileNameIndex = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                if (fileNameIndex >= 0) {
                    val fileName = it.getString(fileNameIndex)
                    name = it.getString(fileNameIndex)
                } else {
                    Log.e("TAG", "Column not found: ${OpenableColumns.DISPLAY_NAME}")
                }
            } else {
                Log.e("TAG", "Empty cursor")
            }
        }
        return name
    }

}