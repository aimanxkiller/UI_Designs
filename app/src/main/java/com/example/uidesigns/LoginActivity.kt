package com.example.uidesigns

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.uidesigns.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    lateinit var binding:ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //binding for login activity
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

}