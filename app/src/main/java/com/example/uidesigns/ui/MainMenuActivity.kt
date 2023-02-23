package com.example.uidesigns.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.uidesigns.R
import com.example.uidesigns.databinding.ActivityMainMenuBinding
import com.example.uidesigns.fragments.*

class MainMenuActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainMenuBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityMainMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFrags(Home())

        binding.bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.home -> replaceFrags(Home())
                R.id.calendar -> replaceFrags(Calendar())
                R.id.history -> replaceFrags(History())
                R.id.profile -> replaceFrags(Profile())

                else ->{
                }
            }
            true
        }

    }

    private fun replaceFrags(fragment:Fragment){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout,fragment)
        fragmentTransaction.commit()
    }
}