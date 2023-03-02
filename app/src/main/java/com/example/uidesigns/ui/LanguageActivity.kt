package com.example.uidesigns.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.uidesigns.databinding.ActivityLanguageBinding

class LanguageActivity : AppCompatActivity() {

    private lateinit var binding:ActivityLanguageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setting binding for language activity
        binding = ActivityLanguageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Temp buttons to go to login activity
        binding.buttonArabic.setOnClickListener { toNextActivity() }
        binding.buttonEnglish.setOnClickListener { toNextActivity() }

    }

    private fun toNextActivity(){
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

}