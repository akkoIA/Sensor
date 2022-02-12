package com.example2.sensor

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.ImageView
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(){
//    private var mManager: SensorManager by Delegates.notNull<SensorManager>()
//    private var mSensor: Sensor by Delegates.notNull<Sensor>()

    var isStart = false
    var isWorking = false

//    var defaultX = 0f
//    var defaultY = 0f
//    var defaultZ = 0f
//
//    var sound1: MediaPlayer? = null
//    var sound2: MediaPlayer? = null
//    var sound3: MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        sound1=MediaPlayer.create(this,R.raw.doamusic)
//        sound2=MediaPlayer.create(this,R.raw.siren)
//        sound3=MediaPlayer.create(this,R.raw.god)

        val text2=findViewById<TextView>(R.id.timer)

        val imageview =findViewById<ImageView>(R.id.imageView)

        imageview.setOnClickListener{
            val intent=Intent(this,MusicActivity::class.java)
            startActivity(intent)
        }

        val timer=object :CountDownTimer(10000,100){
            override fun onTick(p0: Long) {
                text2.text="${p0/1000}"
            }

            override fun onFinish() {
                text2.text="実行中"
                buttonStart.isClickable=true
                buttonStop.isClickable=true

                val intent = Intent(applicationContext, SoundService::class.java)
                startForegroundService(intent)

            }
        }

        buttonStart.setOnClickListener{
            buttonStart.isClickable = false
            buttonStop.isClickable = false
            timer.start()
        }

        buttonStop.setOnClickListener{


            isWorking=false
            text2.text="待機中"

            val intent = Intent(applicationContext, SoundService::class.java)
            stopService(intent)
        }

//
//        mManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
//        //その他のセンサーを取得する場合には引数を違うものに変更する
//        mSensor = mManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
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

//    override fun onSensorChanged(event: SensorEvent?) {
//        if(isStart){
//            defaultX = event?.values?.get(0)?.absoluteValue ?: 0f
//            defaultY = event?.values?.get(1)?.absoluteValue ?: 0f
//            defaultZ = event?.values?.get(2)?.absoluteValue ?: 0f
//
//            isStart = false
//            isWorking=true
//        }
//
//        if(isWorking){
//            //defaultNと現在値の差分を取る
//            if(event?.values == null) return
//            val judX = event.values.get(0).absoluteValue - defaultX > 1
//            val judY = event.values.get(1).absoluteValue - defaultY > 1
//            val judZ = event.values.get(2).absoluteValue - defaultZ > 1
//
//            if(judX || judY || judZ){
//                //音を鳴らす
//                val sharedPref = getSharedPreferences("sound", Context.MODE_PRIVATE)
//                val sound=sharedPref.getString("sound","sound1")
//
//                when(sound){
//                    "sound1" -> sound1?.start()
//                    "sound2" -> sound2?.start()
//                    "sound3" -> sound3?.start()
//                    else -> sound1?.start()
//                }
//            }
//
//        }

//    }

//    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
//    }

    override fun onPause() {
        super.onPause()
//        mManager.unregisterListener(this)
    }

    override fun onResume() {
        super.onResume()
//        mManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_UI)
    }
}
