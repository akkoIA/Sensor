package com.example.sensor

import android.content.Context
import android.hardware.*
import android.media.MediaParser
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.math.absoluteValue
import kotlin.properties.Delegates

class MainActivity : AppCompatActivity(),SensorEventListener{
    private var mManager: SensorManager by Delegates.notNull<SensorManager>()
    private var mSensor: Sensor by Delegates.notNull<Sensor>()

    var isStart = false
    var isWorking = false

    var defaultX = 0f
    var defaultY = 0f
    var defaultZ = 0f

    var sound:MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sound = MediaPlayer.create(this,R.raw.doamusic)

        val text2=findViewById<TextView>(R.id.timer)

        val timer=object :CountDownTimer(10000,100){
            override fun onTick(p0: Long) {
                text2.text="${p0/1000}"
            }

            override fun onFinish() {
                text2.text="実行中"
                buttonStart.visibility=View.GONE
                buttonStop.visibility=View.VISIBLE
                buttonStop.isClickable=true

                isStart = true

            }
        }

        buttonStart.setOnClickListener{
            buttonStart.isClickable = false
            buttonStop.isClickable = false
            timer.start()
        }

        buttonStop.setOnClickListener{
            buttonStart.visibility=View.VISIBLE
            buttonStart.isClickable=true
            buttonStop.visibility= View.GONE

            isWorking=false
            text2.text="待機中"
        }


        mManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        //その他のセンサーを取得する場合には引数を違うものに変更する
        mSensor = mManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
//
//
//
//        val sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
//        val sensor: Sensor? = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
//
//
//
//
//
//        sensorManager.registerListener()
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if(isStart){
            defaultX = event?.values?.get(0)?.absoluteValue ?: 0f
            defaultY = event?.values?.get(1)?.absoluteValue ?: 0f
            defaultZ = event?.values?.get(2)?.absoluteValue ?: 0f

            isStart = false
            isWorking=true
        }

        if(isWorking){
            //defaultNと現在値の差分を取る
            if(event?.values == null) return
            val judX = event.values.get(0).absoluteValue - defaultX > 1
            val judY = event.values.get(1).absoluteValue - defaultY > 1
            val judZ = event.values.get(2).absoluteValue - defaultZ > 1

            if(judX || judY || judZ){
                //音を鳴らす
                sound?.start()
            }

        }

    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
    }

    override fun onPause() {
        super.onPause()
        mManager.unregisterListener(this)
    }

    override fun onResume() {
        super.onResume()
        mManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_UI)
    }
}
