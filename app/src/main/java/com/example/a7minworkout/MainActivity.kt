package com.example.a7minworkout

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.a7minworkout.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private var binding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

//        val btnStart: FrameLayout = findViewById(R.id.btnStart)
        binding?.btnStart?.setOnClickListener{
            val intent = Intent(this, ExerciseActivity::class.java)

            startActivity(intent)
        }

        binding?.btnBMI?.setOnClickListener{
            val intent = Intent(this, BMIActivity::class.java)

            startActivity(intent)
        }

        binding?.btnHistory?.setOnClickListener {
            val intent = Intent(this, HistoryActivity::class.java)

            startActivity(intent)
        }

    }

    override fun onDestroy() {
        super.onDestroy()

        binding = null
    }
}