package com.example.uidesigns.fragments

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import com.example.uidesigns.R


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class Profile : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var languageCurrent:TextView

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
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        val logOut = view.findViewById<CardView>(R.id.cardViewLogout)
        val language= view.findViewById<ConstraintLayout>(R.id.constraint_language)
        languageCurrent = view.findViewById(R.id.languageSel)

        logOut.setOnClickListener {
            openDialog("Do you want to Logout?",0)
        }
        language.setOnClickListener {
            openDialog("Do you want to switch language?",1)
        }

        return view
    }

    private fun openDialog(message:String,type:Int){
        //giving dialog the layout
        val dialogBinding = layoutInflater.inflate(R.layout.custom_dialog,null)
        val dialog = Dialog(requireContext())
        dialog.setContentView(dialogBinding)

        dialog.setCancelable(false)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val cancel: Button = dialog.findViewById(R.id.buttonDialogNo)
        val yes: Button = dialog.findViewById(R.id.buttonDialogYes)
        val content:TextView = dialog.findViewById(R.id.dialog_content_custom)
        val title:TextView = dialog.findViewById(R.id.dialog_title_custom)

        title.text = message
        content.visibility = View.GONE

        cancel.setOnClickListener {
            dialog.dismiss()
        }
        yes.setOnClickListener {
            if (type == 0){
                Toast.makeText(requireContext(),"User clicked logout",Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(requireContext(),"User switched language",Toast.LENGTH_SHORT).show()
                if("Arabic" in languageCurrent.text){
                    languageCurrent.text = "Switch to English"
                    dialog.dismiss()
                }else{
                    languageCurrent.text = "Switch to Arabic"
                    dialog.dismiss()
                }
            }
        }
        dialog.show()

    }

}