package com.example.excercise2_monstarlab

import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Message
import android.view.MotionEvent
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {
    val messageCount: Int = 1
    var number: Int = 0
    var z: Int = 0
    var mHandler: Handler = Handler()
    lateinit var thread: Thread
    var a: Int = 0





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        printDisply()

        btnTru.setOnClickListener {
            number--
            txtSo.text = number.toString()

        }

        btnCong.setOnClickListener {
            number++
            txtSo.text = number.toString()

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
            MotionEvent.ACTION_UP -> setActionUp()
        }
        txtSo.text = number.toString()
    }
    
    fun setDown(m: MotionEvent){

        z= m.getY().toInt()
    }

    fun setMove(m: MotionEvent){
        if (z<m.getY()){
            number--
            txtSo.text = number.toString()
        }else if (z>m.getY()){
            number++
            txtSo.text = number.toString()

        }
        z= m.getY().toInt()


    }

    fun setActionUp(){
        thread = Thread(Runnable {
            if (number > 0) {

                while (number > 0){
                    number--
                    var msg: Message = Message()
                    msg.what = messageCount
                    msg.arg1 = number
                    mHandler.sendMessage(msg)
                    Thread.sleep(100)

                }

                number = 0
            }
            else{

                while (number < 0){
                    number++
                    var msg: Message = Message()
                    msg.what = messageCount
                    msg.arg1 = number
                    mHandler.sendMessage(msg)
                    Thread.sleep(100)

                }

                number = 0
            }
        })
        thread.start()

    }

    fun printDisply(){

        mHandler = Handler(){
            txtSo.text = it.arg1.toString()
            true
        }

    }

}