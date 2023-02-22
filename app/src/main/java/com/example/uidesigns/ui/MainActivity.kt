package com.example.uidesigns.ui

import android.animation.ObjectAnimator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.example.uidesigns.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setting binding for main
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Setting up progress bar (Fake loading currently)
        progressBar()

        //Delaying to launch next activity awaiting progress bar
        lifecycleScope.launch(Dispatchers.Main){
            delay(3500)
            val intent = Intent(this@MainActivity, LanguageActivity::class.java)
            startActivity(intent)
            finish()
        }

    }

    private fun progressBar(){
        val bar = binding.progressBar
        bar.max = 10000
        val currentProgress = 9000

        ObjectAnimator.ofInt(bar,
            "progress",
            currentProgress)
            .setDuration(3000)
            .start()
    }

}