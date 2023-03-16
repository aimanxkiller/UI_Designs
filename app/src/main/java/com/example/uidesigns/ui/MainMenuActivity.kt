package com.example.uidesigns.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.ScaleAnimation
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.uidesigns.R
import com.example.uidesigns.databinding.ActivityMainMenuBinding
import com.example.uidesigns.fragments.*

class MainMenuActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainMenuBinding
    private var selectedTab = 1

    private lateinit var homeLayout : LinearLayout
    private lateinit var scheduleLayout: LinearLayout
    private lateinit var historyLayout: LinearLayout
    private lateinit var profileLayout: LinearLayout

    private lateinit var homeIcon : ImageView
    private lateinit var scheduleIcon: ImageView
    private lateinit var historyIcon: ImageView
    private lateinit var profileIcon: ImageView

    private lateinit var homeTxt : TextView
    private lateinit var scheduleTxt : TextView
    private lateinit var historyTxt : TextView
    private lateinit var profileTxt : TextView

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityMainMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //replacing default fragment to home view
        replaceFrags(Home())

        homeLayout = binding.homeNavLayout
        scheduleLayout = binding.scheduleNavLayout
        historyLayout = binding.historyNavLayout
        profileLayout = binding.profileNavLayout

        homeIcon = binding.homeIcon
        scheduleIcon = binding.scheduleIcon
        historyIcon = binding.historyIcon
        profileIcon = binding.profileIcon

        homeTxt = binding.homeTxt
        scheduleTxt = binding.scheduleTxt
        historyTxt = binding.historyTxt
        profileTxt = binding.profileTxt

        homeLayout.setOnClickListener {
            if (selectedTab !=1){
                scheduleTxt.visibility = View.GONE
                historyTxt.visibility = View.GONE
                profileTxt.visibility = View.GONE

                scheduleIcon.setImageResource(R.drawable.ic_cal_off)
                historyIcon.setImageResource(R.drawable.ic_clock_off)
                profileIcon.setImageResource(R.drawable.ic_profile_off)

                scheduleLayout.setBackgroundColor(getColor(R.color.transparent))
                historyLayout.setBackgroundColor(getColor(R.color.transparent))
                profileLayout.setBackgroundColor(getColor(R.color.transparent))

                //set as selected
                homeLayout.setBackgroundResource(R.drawable.background_selected)
                homeIcon.setImageResource(R.drawable.ic_home_nav)
                homeTxt.visibility = View.VISIBLE

                replaceFrags(Home())

                selectedTab = 1

                //animation
//                callAnimation(homeLayout)
            }
        }

        scheduleLayout.setOnClickListener {
            if (selectedTab !=2){
                homeTxt.visibility = View.GONE
                historyTxt.visibility = View.GONE
                profileTxt.visibility = View.GONE

                homeIcon.setImageResource(R.drawable.ic_home_off)
                historyIcon.setImageResource(R.drawable.ic_clock_off)
                profileIcon.setImageResource(R.drawable.ic_profile_off)

                homeLayout.setBackgroundColor(getColor(R.color.transparent))
                historyLayout.setBackgroundColor(getColor(R.color.transparent))
                profileLayout.setBackgroundColor(getColor(R.color.transparent))

                //set as selected
                scheduleLayout.setBackgroundResource(R.drawable.background_selected)
                scheduleIcon.setImageResource(R.drawable.ic_cal_nav)
                scheduleTxt.visibility = View.VISIBLE

                replaceFrags(Calendar())

                selectedTab = 2

                //animation
//                callAnimation(scheduleLayout)
            }
        }

        historyLayout.setOnClickListener {
            if (selectedTab !=3){
                homeTxt.visibility = View.GONE
                scheduleTxt.visibility = View.GONE
                profileTxt.visibility = View.GONE

                homeIcon.setImageResource(R.drawable.ic_home_off)
                scheduleIcon.setImageResource(R.drawable.ic_cal_off)
                profileIcon.setImageResource(R.drawable.ic_profile_off)

                homeLayout.setBackgroundColor(getColor(R.color.transparent))
                scheduleLayout.setBackgroundColor(getColor(R.color.transparent))
                profileLayout.setBackgroundColor(getColor(R.color.transparent))

                //set as selected
                historyLayout.setBackgroundResource(R.drawable.background_selected)
                historyIcon.setImageResource(R.drawable.ic_clock_nav)
                historyTxt.visibility = View.VISIBLE

                replaceFrags(History())

                selectedTab = 3

                //animation
//                callAnimation(scheduleLayout)
            }
        }

        profileLayout.setOnClickListener {
            if (selectedTab !=4){
                homeTxt.visibility = View.GONE
                historyTxt.visibility = View.GONE
                scheduleTxt.visibility = View.GONE

                homeIcon.setImageResource(R.drawable.ic_home_off)
                historyIcon.setImageResource(R.drawable.ic_clock_off)
                scheduleIcon.setImageResource(R.drawable.ic_cal_off)

                homeLayout.setBackgroundColor(getColor(R.color.transparent))
                historyLayout.setBackgroundColor(getColor(R.color.transparent))
                scheduleLayout.setBackgroundColor(getColor(R.color.transparent))

                //set as selected
                profileLayout.setBackgroundResource(R.drawable.background_selected)
                profileIcon.setImageResource(R.drawable.ic_profile_nav)
                profileTxt.visibility = View.VISIBLE

                replaceFrags(Profile())

                selectedTab = 4

                //animation
//                callAnimation(scheduleLayout)
            }
        }
//        //Setting navigation based on bottom nav bar replacing fragment accordingly
//        binding.bottomNavigationView.setOnItemSelectedListener {
//            when(it.itemId){
//                R.id.home -> replaceFrags(Home())
//                R.id.calendar -> replaceFrags(Calendar())
//                R.id.history -> replaceFrags(History())
//                R.id.profile -> replaceFrags(Profile())
//            }
//            true
//        }

    }

    private fun callAnimation(layout: LinearLayout) {
        val anim = ScaleAnimation(
            0.2f,  // Start scale X
            1.0f,  // End scale X
            1.0f,  // Start scale Y
            1.0f,  // End scale Y
            Animation.RELATIVE_TO_SELF,  // Pivot X type
            0.0f,  // Pivot X value
            Animation.RELATIVE_TO_SELF,  // Pivot Y type
            0.0f  // Pivot Y value
        )
        anim.fillAfter = true
        anim.duration = 500
        layout.startAnimation(anim)
    }

    //function to replace fragments
    private fun replaceFrags(fragment:Fragment){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout,fragment)
        fragmentTransaction.commit()
    }

}