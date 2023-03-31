package com.example.a7minworkout

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.a7minworkout.databinding.ActivityHistoryBinding
import com.example.a7minworkout.databinding.DialogDeleteCustomBinding
import kotlinx.coroutines.launch

class HistoryActivity : AppCompatActivity() {

    private var binding: ActivityHistoryBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)

        setContentView(binding?.root)
        setSupportActionBar(binding?.toolHistory)

        if(supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.title = "Your Historic"
        }

        binding?.toolHistory?.setNavigationOnClickListener {
            onBackPressed()
        }

        val historyDao = (application as HistoryApp).db.historyDao()
        getAllCompletedDates(historyDao)
    }

    private fun getAllCompletedDates(historyDao: HistoryDao){
        lifecycleScope.launch {
            historyDao.fetchAllDates().collect(){ allCompletedDatesList ->
                if(allCompletedDatesList.isNotEmpty()){
                    binding?.txtExerciseCompleted?.visibility = View.VISIBLE
                    binding?.itemsListHistory?.visibility = View.VISIBLE
                    binding?.txtNoRecordsAvailable?.visibility = View.INVISIBLE

                    binding?.itemsListHistory?.layoutManager = LinearLayoutManager(this@HistoryActivity)

                    val dates = ArrayList<String>()
                    for(date in allCompletedDatesList){
                        dates.add(date.date)
                    }
                    val historyAdapter = HistoryAdapter(dates)
                    {
                        dateString ->
                        deleteDateDialog(dateString, historyDao)
                    }

                    binding?.itemsListHistory?.adapter = historyAdapter

                }else{
                    binding?.txtExerciseCompleted?.visibility = View.GONE
                    binding?.itemsListHistory?.visibility = View.GONE
                    binding?.txtNoRecordsAvailable?.visibility = View.VISIBLE
                }
            }
        }
    }

//region Function deleteDateDialog()
    private fun deleteDateDialog(date:String, historyDao: HistoryDao){

        val customDeletedDialog = Dialog(this@HistoryActivity)
        val dialogBindingDelete = DialogDeleteCustomBinding.inflate(layoutInflater)

        customDeletedDialog.setContentView(dialogBindingDelete.root)
        customDeletedDialog.setCanceledOnTouchOutside(false)

        dialogBindingDelete.btnDeleteYes.setOnClickListener {
            val history = HistoryEntity()
            lifecycleScope.launch {
                historyDao.delete(HistoryEntity(date))
                Toast.makeText(
                    applicationContext,
                    "Date ${history.date} was deleted successfully",
                    Toast.LENGTH_LONG
                ).show()

                customDeletedDialog.dismiss()
            }
        }
            dialogBindingDelete.btnDeleteNo.setOnClickListener {
                customDeletedDialog.dismiss()
            }
            customDeletedDialog.show()
    }
//endregion

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}