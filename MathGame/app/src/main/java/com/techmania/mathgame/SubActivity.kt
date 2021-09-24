package com.techmania.mathgame

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import java.util.*
import kotlin.random.Random

class SubActivity : AppCompatActivity() {

    lateinit var textScore : TextView
    lateinit var textLife : TextView
    lateinit var textTime : TextView

    lateinit var textQuestion : TextView
    lateinit var editTextAnswer : EditText

    lateinit var buttonOk : Button
    lateinit var buttonNext : Button

    var correctAnswer = 0
    var userScore = 0
    var userLife = 3

    lateinit var timer : CountDownTimer
    private val startTimerInMillis : Long = 20000
    var timeLeftInMillis : Long = startTimerInMillis

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sub)

        supportActionBar!!.title = "Subtraction"

        textScore = findViewById(R.id.textViewScoreSub)
        textLife = findViewById(R.id.textViewLifeSub)
        textTime = findViewById(R.id.textViewTimeSub)
        textQuestion = findViewById(R.id.textViewQuestionSub)
        editTextAnswer = findViewById(R.id.editTextAnswerSub)
        buttonOk = findViewById(R.id.buttonOkSub)
        buttonNext = findViewById(R.id.buttonNextSub)

        gameContinue()

        buttonOk.setOnClickListener {

            val input = editTextAnswer.text.toString()

            if (input == "")
            {
                Toast.makeText(applicationContext,"Please write an answer or click the next button",
                    Toast.LENGTH_LONG).show()
            }
            else
            {
                pauseTimer()

                val userAnswer = input.toInt()

                if (userAnswer == correctAnswer)
                {
                    userScore = userScore + 10
                    textQuestion.text = "Congratulations, your answer is correct"
                    textScore.text = userScore.toString()
                }
                else
                {
                    userLife--
                    textQuestion.text = "Sorry, your answer is wrong"
                    textLife.text = userLife.toString()
                }

            }

        }

        buttonNext.setOnClickListener {

            pauseTimer()
            resetTimer()

            editTextAnswer.setText("")

            if (userLife == 0)
            {
                Toast.makeText(applicationContext,"Game Over", Toast.LENGTH_LONG).show()
                val intent = Intent(this@SubActivity,ResultActivity::class.java)
                intent.putExtra("score",userScore)
                startActivity(intent)
                finish()
            }
            else
            {
                gameContinue()
            }

        }

    }

    fun gameContinue()
    {
        val number1 = Random.nextInt(0,100)
        val number2 = Random.nextInt(0,100)

        if (number1 >= number2)
        {
            textQuestion.text = "$number1 - $number2"
            correctAnswer = number1 - number2
        }
        else
        {
            textQuestion.text = "$number2 - $number1"
            correctAnswer = number2 - number1
        }

        startTimer()

    }

    fun startTimer()
    {
        timer = object : CountDownTimer(timeLeftInMillis,1000){

            override fun onTick(millisUntilFinished : Long) {

                timeLeftInMillis = millisUntilFinished
                updateText()

            }

            override fun onFinish() {

                pauseTimer()
                resetTimer()
                updateText()

                userLife--
                textLife.text = userLife.toString()
                textQuestion.text = "Sorry, Time is up!"

            }


        }.start()
    }

    fun updateText()
    {
        val remainingTime : Int = (timeLeftInMillis / 1000).toInt()
        textTime.text = String.format(Locale.getDefault(),"%02d",remainingTime)
    }

    fun pauseTimer()
    {
        timer.cancel()
    }

    fun resetTimer()
    {
        timeLeftInMillis = startTimerInMillis
        updateText()
    }
}