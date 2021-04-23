package com.example.excercise2_monstarlab

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Message
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {
    var number: Int? = 0
    var y: Int = 0
    var mHandler: Handler = Handler()
    lateinit var increases: Runnable
    lateinit var decreases: Runnable
    lateinit var backZero: Runnable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        backZero = Runnable {
            if (number!! > 0){
                number = number!! - 1
                txtNumber.text = number.toString()
                btnCong.setBackgroundColor(resources.getColor(R.color.teal_200))
                mHandler.postDelayed(backZero,100)
            }
            else if (number!! < 0){
                number = number!! + 1
                txtNumber.text = number.toString()
                btnTru.setBackgroundColor(resources.getColor(R.color.teal_200))
                mHandler.postDelayed(backZero,100)
            }
            else {
                mHandler.removeCallbacks(backZero)
            }
        }
       increases = Runnable {
           mHandler.removeCallbacks(backZero)
           number = number!! + 1
           if (btnCong.isPressed){
               mHandler.postDelayed(increases,100)
               txtNumber.text = number.toString()
               randomColor(number!!)
           }
           btnCong.setBackgroundColor(resources.getColor(R.color.green))
           mHandler.postDelayed(backZero, 2000)
       }
        decreases = Runnable {
            mHandler.removeCallbacks(backZero)
            number = number!! - 1
            if (btnTru.isPressed){
                mHandler.postDelayed(decreases,100)
                txtNumber.text = number.toString()
                randomColor(number!!)
            }
            btnTru.setBackgroundColor(resources.getColor(R.color.green))
            mHandler.postDelayed(backZero,2000)
        }



        btnCong.setOnClickListener {
            mHandler.removeCallbacks(backZero)
            number = number!! + 1
            txtNumber.text = number.toString()
            mHandler.postDelayed(backZero,2000)
        }
        btnTru.setOnClickListener {
            mHandler.removeCallbacks(backZero)
            number = number!! - 1
            txtNumber.text = number.toString()
            mHandler.postDelayed(backZero,2000)
        }
        btnCong.setOnLongClickListener {
            mHandler.post(increases)
        }
        btnTru.setOnLongClickListener {
            mHandler.post(decreases)
        }
        rvActivity.setOnTouchListener {
            v, event ->  handleTouch(event)
            true
        }
    }

    fun handleTouch(m: MotionEvent){
        var action = m.actionMasked
        when(action){
            MotionEvent.ACTION_DOWN -> {
                mHandler.removeCallbacks(backZero)
                y= m.getY().toInt()
            }
            MotionEvent.ACTION_MOVE -> {
                if (y<m.getY()){
                    number = number!! - 1
                    txtNumber.text = number.toString()
                    randomColor(number!!)
                }else if (y>m.getY()){
                    number = number!! + 1
                    txtNumber.text = number.toString()
                    randomColor(number!!)
                }
                else {
                    number=number
                    txtNumber.text = number!!.toString()
                }
                y = m.getY().toInt()
            }
            MotionEvent.ACTION_UP -> {
                mHandler.postDelayed(backZero,2000)
            }
        }
    }

    fun randomColor(number: Int){
        var random: Random = Random()
        val color: Int = Color.argb(255,random.nextInt(256),random.nextInt(256),random.nextInt(256))
        if (number % 100 == 0) {
            txtNumber.setTextColor(color)
        }
    }

}