package com.example.uidesigns

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

        progressBar()

        lifecycleScope.launch(Dispatchers.Main){
            delay(5000)
            val intent = Intent(this@MainActivity,LanguageActivity::class.java)
            startActivity(intent)
        }

    }

    private fun progressBar(){
        val bar = binding.progressBar
        bar.max = 1000
        val currentProgress = 1000

        ObjectAnimator.ofInt(bar,"progress",currentProgress)
            .setDuration(3000).start()
    }

}