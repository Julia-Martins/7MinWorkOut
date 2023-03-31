package com.example.a7minworkout

import android.R
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.provider.FontsContractCompat
import androidx.lifecycle.lifecycleScope
import com.example.a7minworkout.databinding.ActivityEndBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class FinishActivity : AppCompatActivity() {

    private var binding: ActivityEndBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEndBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setSupportActionBar(binding?.toolbarFinishActivity)

        binding?.toolbarFinishActivity?.setSubtitleTextColor(Color.parseColor("#FFFFFF"))

        if(supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }

        binding?.toolbarFinishActivity?.setNavigationOnClickListener {
            onBackPressed()
        }

        binding?.btnFinish?.setOnClickListener {
            finish()
        }

        val historyDao = (application as HistoryApp).db.historyDao()
        addDateToDataBase(historyDao)


    }

    private fun addDateToDataBase(historyDao: HistoryDao){
        val c = Calendar.getInstance()
        val dateTime = c.time
        Log.e("Date: ", "" + dateTime)

        val sdf = SimpleDateFormat("dd MMM yyyy HH:mm:ss", Locale.getDefault())
        val date = sdf.format(dateTime)
        Log.e("Formatted Date: ", "" + date)


        lifecycleScope.launch {
            historyDao.insert(HistoryEntity(date=date))
            Log.e(
                "Date : ",
                "Added..."
            )
        }

    }
}