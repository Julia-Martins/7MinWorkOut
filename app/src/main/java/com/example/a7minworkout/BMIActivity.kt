package com.example.a7minworkout

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.a7minworkout.databinding.ActivityBmiBinding
import java.math.BigDecimal
import java.math.RoundingMode

class BMIActivity : AppCompatActivity() {

    companion object{
        private const val METRIC_UNITS_VIEW = "METRIC_UNITS_VIEW"
        private const val US_UNITS_VIEW = "US_UNITS_VIEW"
    }

    private var binding: ActivityBmiBinding? = null

    private var currentVisibleView: String = METRIC_UNITS_VIEW

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBmiBinding.inflate(layoutInflater)

        setContentView(binding?.root)
        setSupportActionBar(binding?.toolBarBMI)

        if(supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.title = "Calculate BMI"
        }

        binding?.toolBarBMI?.setNavigationOnClickListener {
            onBackPressed()
        }

        makeMetricVisible()

        binding?.rgUnits?.setOnCheckedChangeListener { _, checkedId: Int ->
            if(checkedId == R.id.btnRadioMetricsUnits){
                makeMetricVisible()
            }else{
                usUnitsVisible()
            }
        }

        binding?.btnCalculate?.setOnClickListener {
            calculateUnits()
        }
    }

    private fun makeMetricVisible(){
        currentVisibleView = METRIC_UNITS_VIEW
        binding?.txtInputLayoutWeight?.visibility = View.VISIBLE
        binding?.txtInputLayoutHeight?.visibility = View.VISIBLE
        binding?.txtInputFeet?.visibility = View.GONE
        binding?.txtInputInch?.visibility = View.GONE
        binding?.txtInputUSWeight?.visibility = View.INVISIBLE

        binding?.txtDigWeight?.text!!.clear()
        binding?.txtDigHeight?.text!!.clear()

        binding?.layoutResult?.visibility = View.INVISIBLE

    }

    private fun usUnitsVisible(){
        currentVisibleView = US_UNITS_VIEW
        binding?.txtInputInch?.visibility = View.VISIBLE
        binding?.txtInputFeet?.visibility = View.VISIBLE
        binding?.txtInputUSWeight?.visibility = View.VISIBLE

        binding?.txtInputLayoutWeight?.visibility = View.GONE
        binding?.txtInputLayoutHeight?.visibility = View.INVISIBLE

        binding?.txtInch?.text!!.clear()
        binding?.txtFeet?.text!!.clear()
        binding?.txtUSWeight?.text!!.clear()

        binding?.layoutResult?.visibility = View.INVISIBLE
    }

    private fun displayBMIResult(bmi: Float){
        val bmiLabel: String
        val bmiDescription: String

        if(bmi.compareTo(15f) <= 0){
            bmiLabel = "Very severely UNDERWEIGHT"
            bmiDescription = "You really need to take better care of yourself! Eat more!"
        }else if(bmi.compareTo(15f) > 0 && bmi.compareTo(16f) <= 0){
            bmiLabel = "Very severely UNDERWEIGHT"
            bmiDescription = "You really need to take better care of yourself! Eat more!"
        }else if(bmi.compareTo(16f) > 0 && bmi.compareTo(18.5f) <= 0){
            bmiLabel = "Underweight"
            bmiDescription = "You really need to take better care of yourself! Eat more!"
        }else if(bmi.compareTo(18.5f) > 0 && bmi.compareTo(25f) <= 0){
            bmiLabel = "Normal"
            bmiDescription = "Congratulations! You are in a good shape!"
        }else if(bmi.compareTo(25f) > 0 && bmi.compareTo(30f) <= 0){
            bmiLabel = "Overweight"
            bmiDescription = "You really need to take better care of yourself! Workout maybe!"
        }else if(bmi.compareTo(30f) > 0 && bmi.compareTo(35f) <= 0){
            bmiLabel = "Obese Class | Moderately Obese"
            bmiDescription = "You really need to take better care of yourself! Workout maybe!"
        }else if(bmi.compareTo(35f) > 0 && bmi.compareTo(40f) <= 0){
            bmiLabel = "Obese Class || Severely Obese"
            bmiDescription = "OMG! You are in a very dangerous condition! Act now!"
        }else{
            bmiLabel = "Obese Class ||| Very Severely Obese"
            bmiDescription = "OMG! You are in a very dangerous condition! Act now!"
        }

        val bmiValue = BigDecimal(bmi.toDouble()).setScale(2, RoundingMode.HALF_EVEN).toString()

        binding?.layoutResult?.visibility = View.VISIBLE
        binding?.txtBMIValue?.text = bmiValue
        binding?.txtBMIType?.text = bmiLabel
        binding?.txtBMIDescription?.text = bmiDescription
    }

    private fun validateMetricUnits(): Boolean{
        var isValid = true

        if(binding?.txtDigWeight?.text.toString().isEmpty()){
            isValid = false
        }else if(binding?.txtDigHeight?.text.toString().isEmpty()){
            isValid = false
        }

        return isValid
    }

    private fun validateUsUnits(): Boolean{
        var isValid = true

        if(binding?.txtFeet?.text.toString().isEmpty()){
            isValid = false
        }else if(binding?.txtInch?.text.toString().isEmpty()){
            isValid = false
        }else if(binding?.txtUSWeight?.text.toString().isEmpty()){
            isValid = false
        }

        return isValid
    }

    private fun calculateUnits(){
        if(currentVisibleView == METRIC_UNITS_VIEW) {
            if (validateMetricUnits()) {
                val weightValue: Float = binding?.txtDigWeight?.text.toString().toFloat()
                val heightValue: Float = binding?.txtDigHeight?.text.toString().toFloat() / 100

                val bmi = weightValue / (heightValue * heightValue)

                displayBMIResult(bmi)

            } else {
                Toast.makeText(
                    this@BMIActivity,
                    "Please enter valid values.",
                    Toast.LENGTH_SHORT
                )
                    .show()
            }
        }else{
            if(validateUsUnits()){
                val txtUsFeet: String = binding?.txtFeet?.text.toString()
                val txtUsInch: String = binding?.txtInch?.text.toString()
                val txtUsWeight: Float = binding?.txtUSWeight?.text.toString().toFloat()

                val heightVal = txtUsInch.toFloat() + txtUsFeet.toFloat() * 12

                val bmi = 703 * (txtUsWeight / (heightVal * heightVal))

                displayBMIResult(bmi)
            }else {
                Toast.makeText(
                    this@BMIActivity,
                    "Please enter valid values.",
                    Toast.LENGTH_SHORT
                )
                    .show()
            }
        }
    }
}