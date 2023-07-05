package com.example.a7minworkout

import android.app.Dialog
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.a7minworkout.databinding.ActivityExerciseBinding
import com.example.a7minworkout.databinding.DialogCustomBinding
import java.util.*

class ExerciseActivity : AppCompatActivity(), TextToSpeech.OnInitListener {
    private var binding: ActivityExerciseBinding? = null

    private var countDownTimer: CountDownTimer? = null
    private var restProgress = 0

    private var restTimerDuration: Long = 1
    private var restTimerDurationThirdSec: Long = 3

    private var exerciseList: ArrayList<ExerciseModel>?= null
    private var currentExercisePosition = -1

    private var tts: TextToSpeech? = null
    private var player: MediaPlayer? = null

    private var exerciseAdapter: ExerciseStatusAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExerciseBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setSupportActionBar(binding?.toolbarExercise)

        if(supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }

        binding?.toolbarExercise?.setNavigationOnClickListener {
            customDialogForBackButton()
        }

        exerciseList = Constants.defaultExerciseList()

        setUpRestView()
        setRestProgressBar()

        setUpExerciseStatusRecycleView()

        tts = TextToSpeech(this, this)

        try{
            val soundRI = Uri.parse("android.resource://com.example.a7minworkout/"
                    + R.raw.overture)

            player = MediaPlayer.create(applicationContext, soundRI)
            player?.isLooping = false
            player?.start()

        }catch (e: Exception){
            e.printStackTrace()
        }

// region Parts of the example
//        binding?.txtTimer?.text = "${(timerDuration/1000).toString()}"

//        binding?.btnPause?.setOnClickListener {
//            pauseTimer()
//        }
//
//        binding?.btnReset?.setOnClickListener {
//            resetTimer()
//        }

// endregion

    }

    private fun customDialogForBackButton(){
        val customDialog = Dialog(this)
        val dialogBinding = DialogCustomBinding.inflate(layoutInflater)

        customDialog.setContentView(dialogBinding.root)
        customDialog.setCanceledOnTouchOutside(false)
        dialogBinding.btnYes.setOnClickListener {
            this@ExerciseActivity.finish()
            customDialog.dismiss()
        }
        dialogBinding.btnNo.setOnClickListener {
            customDialog.dismiss()
        }

        customDialog.show()

    }

    private fun setUpExerciseStatusRecycleView(){
        binding?.rvExerciseNumber?.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        exerciseAdapter = ExerciseStatusAdapter(exerciseList!!)

        binding?.rvExerciseNumber?.adapter = exerciseAdapter
    }

    private fun setUpRestView(){
        if(countDownTimer != null){
            countDownTimer?.cancel()
            restProgress = 0
        }
    }

    private fun setRestProgressBar(){

        binding?.txtNextExercise?.text = exerciseList!![currentExercisePosition +1].getName()

        binding?.progressBar?.progress = restProgress

        countDownTimer = object: CountDownTimer(restTimerDuration*1000, 1000){
            override fun onTick(millisUntilFinished: Long) {
                restProgress++

                binding?.progressBar?.progress = 10 - restProgress
                binding?.txtTimer?.text = (10 - restProgress).toString()
            }

            override fun onFinish() {
                currentExercisePosition++

                exerciseList!![currentExercisePosition].setIsSelected(true)
                exerciseAdapter!!.notifyDataSetChanged()

                setUpRestView()
                speakOut(exerciseList!![currentExercisePosition ].getName())
                binding?.frProgressBarThirdSec?.visibility = View.VISIBLE
                binding?.txtExerciseName?.visibility = View.VISIBLE

                binding?.txtTittle?.visibility = View.INVISIBLE
                binding?.frProgressBar?.visibility = View.INVISIBLE
                binding?.txtNextExerciseTittle?.visibility = View.INVISIBLE
                binding?.txtNextExercise?.visibility = View.INVISIBLE

                binding?.imgExercises?.visibility = View.VISIBLE

                binding?.imgExercises?.setImageResource(exerciseList!![currentExercisePosition].getImage())
                binding?.txtExerciseName?.text = exerciseList!![currentExercisePosition].getName()

                setRestProgressBarThirdSec()

            }
        }.start()
    }

    private fun setRestProgressBarThirdSec(){
        binding?.progressBarThirdSec?.progress = restProgress

        countDownTimer = object: CountDownTimer(restTimerDurationThirdSec*1000, 1000){
            override fun onTick(millisUntilFinished: Long) {
                restProgress++

                binding?.progressBarThirdSec?.progress = 30 - restProgress
                binding?.txtTimerThirdSec?.text = (30 - restProgress).toString()
            }

            override fun onFinish() {
                if(currentExercisePosition < exerciseList?.size!! - 1){

                    exerciseList!![currentExercisePosition].setIsSelected(false)
                    exerciseList!![currentExercisePosition].setIsCompleted(true)
                    exerciseAdapter!!.notifyDataSetChanged()

                    setUpRestView()

                    binding?.frProgressBarThirdSec?.visibility = View.INVISIBLE
                    binding?.txtExerciseName?.visibility = View.INVISIBLE

                    binding?.txtTittle?.visibility = View.VISIBLE
                    binding?.frProgressBar?.visibility = View.VISIBLE
                    binding?.txtNextExerciseTittle?.visibility = View.VISIBLE
                    binding?.txtNextExercise?.visibility = View.VISIBLE

                    binding?.imgExercises?.visibility = View.GONE

                    setRestProgressBar()

                }else{
                    finish()

                    val intent = Intent(this@ExerciseActivity, FinishActivity::class.java)

                    startActivity(intent)
                }
            }
        }.start()
    }

    override fun onDestroy() {
        super.onDestroy()

        if(countDownTimer != null){
            countDownTimer?.cancel()
            restProgress = 0
        }

        if(player != null){
            player!!.stop()
        }

        if(tts != null){
            tts?.stop()
            tts?.shutdown()
        }

        binding = null
    }

    override fun onInit(status: Int) {
        if(status == TextToSpeech.SUCCESS){
            val result = tts!!.setLanguage(Locale.US)

            if(result == TextToSpeech.LANG_MISSING_DATA ||
                result == TextToSpeech.LANG_NOT_SUPPORTED){
                Log.e("TTS", "The Language specified is not supported")
            }
        }else{
            Log.e("TTS", "Initialization Failed!")
        }
    }

    private fun speakOut(text: String){
        tts?.speak(text, TextToSpeech.QUEUE_FLUSH, null, "")
    }

// region Some examples of using timers

//    private fun startTimer(pauseOffsetL: Long){
//        countDownTimer = object: CountDownTimer(timerDuration - pauseOffsetL, 1000){
//            override fun onTick(millisUntilFinished: Long) {
//                pauseOffset = timerDuration - millisUntilFinished
//
//                binding?.txtTimer?.text = (millisUntilFinished / 1000).toString()
//            }
//
//            override fun onFinish() {
//                Toast.makeText(this@ExerciseActivity, "Timer is finished", Toast.LENGTH_SHORT).show()
//            }
//        }.start()
//    }
//
//    private fun pauseTimer(){
//        if(countDownTimer != null){
//            countDownTimer!!.cancel()
//        }
//    }
//
//    private fun resetTimer(){
//        if(countDownTimer != null){
//            countDownTimer!!.cancel()
//            binding?.txtNum?.text = "${(timerDuration/1000).toString()}"
//            countDownTimer = null
//            pauseOffset = 0
//        }
//    }

// endregion
}