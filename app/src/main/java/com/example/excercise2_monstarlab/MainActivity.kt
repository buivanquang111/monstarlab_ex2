package com.example.excercise2_monstarlab

import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Message
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {
    val messageCount: Int = 1
    var number: Int? = 0
    var z: Int = 0
    var mHandler: Handler = Handler()
    lateinit var thread: Thread
    var increase: CountDownTimer? = null
    var decrease: CountDownTimer? = null
    var isMoving: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        printDisply()

        increase = object : CountDownTimer(Long.MAX_VALUE,100){
            override fun onFinish() {
                TODO("Not yet implemented")
            }

            override fun onTick(millisUntilFinished: Long) {
                number = number!! + 1
                txtSo.text = number.toString()
                if (number == 0){
                    increase?.cancel()

                }
            }

        }
        decrease = object : CountDownTimer(Long.MAX_VALUE,100){
            override fun onFinish() {
                TODO("Not yet implemented")
            }

            override fun onTick(millisUntilFinished: Long) {
                number = number!! -1
                txtSo.text = number.toString()
                if (number == 0){
                    decrease?.cancel()

                }
            }

        }

        btnCong.setOnTouchListener {
            v, event ->
            if (event.action == MotionEvent.ACTION_DOWN){
                btnCong.setBackgroundColor(resources.getColor(R.color.green))
                increase?.start()
                decrease?.cancel()
            }
            if (event.action == MotionEvent.ACTION_UP){
                btnCong.setBackgroundColor(resources.getColor(R.color.teal_200))
                increase?.cancel()
                delay({decrease?.start()},1000)
            }

            false
        }
        btnTru.setOnTouchListener {
            v, event ->
            if (event.action == MotionEvent.ACTION_DOWN){
                btnTru.setBackgroundColor(resources.getColor(R.color.green))
                decrease?.start()
                increase?.cancel()
            }
            if (event.action == MotionEvent.ACTION_UP){
                btnTru.setBackgroundColor(resources.getColor(R.color.teal_200))
                decrease?.cancel()
                delay({increase?.start()},1000)
            }
            false
        }

        rvActivity.setOnTouchListener {
            v, event ->  handleTouch(event)
            true
        }


    }


    //xu li vuot
    fun handleTouch(m: MotionEvent){
        var action = m.actionMasked
        when(action){
            MotionEvent.ACTION_DOWN ->setDown(m)
            MotionEvent.ACTION_MOVE ->setMove(m)
            MotionEvent.ACTION_UP -> delay({setActionUp()},1000)
        }
        txtSo.text = number.toString()
    }

    fun setDown(m: MotionEvent){

        z= m.getY().toInt()
    }

    fun setMove(m: MotionEvent){
        if (z<m.getY()){
            number = number!! - 1
            txtSo.text = number.toString()
        }else if (z>m.getY()){
            number = number!! + 1
            txtSo.text = number.toString()

        }
        z= m.getY().toInt()


    }

    fun setActionUp(){

        thread = Thread(Runnable {
            if (number!! > 0) {

                while (number!! > 0){
                    number = number!! - 1
                    var msg: Message = Message()
                    msg.what = messageCount
                    msg.arg1 = number!!
                    mHandler.sendMessage(msg)
                    Thread.sleep(100)

                }
                number = 0
            }
            else{

                while (number!! < 0){
                    number = number!! + 1
                    var msg: Message = Message()
                    msg.what = messageCount
                    msg.arg1 = number!!
                    mHandler.sendMessage(msg)
                    Thread.sleep(100)

                }
                number = 0
            }
        })
        thread.start()

    }
    fun delay(function: () -> Unit,delay: Long){
        mHandler.postDelayed(function,delay)
    }


    fun printDisply(){

        mHandler = Handler(){
            txtSo.text = it.arg1.toString()
            true
        }

    }

}