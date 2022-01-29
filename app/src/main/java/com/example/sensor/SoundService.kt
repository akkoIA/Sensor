package com.example.sensor

import android.app.Service
import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.media.MediaPlayer
import android.os.CountDownTimer
import android.os.IBinder
import android.view.View
import androidx.core.app.NotificationCompat
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.math.absoluteValue
import kotlin.properties.Delegates

class SoundService : Service(), SensorEventListener {
    private var mManager: SensorManager by Delegates.notNull<SensorManager>()
    private var mSensor: Sensor by Delegates.notNull<Sensor>()

    var isStart = false
    var isWorking = false

    var defaultX = 0f
    var defaultY = 0f
    var defaultZ = 0f

    var sound1: MediaPlayer? = null
    var sound2: MediaPlayer? = null
    var sound3: MediaPlayer? = null

    override fun onCreate() {
        super.onCreate()
        sound1=MediaPlayer.create(this,R.raw.doamusic)
        sound2=MediaPlayer.create(this,R.raw.siren)
        sound3=MediaPlayer.create(this,R.raw.god)

    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        mManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        //その他のセンサーを取得する場合には引数を違うものに変更する
        mSensor = mManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

        isStart = true

        return START_STICKY
    }

    override fun onSensorChanged(event: SensorEvent?) {
        val notification = NotificationCompat.Builder(this,"doabell")
            .setContentTitle("ドアベル起動中")
            .setContentText("ドアベル起動中")
            .setSmallIcon(R.mipmap.ic_launcher)
            .build()
        startForeground(1, notification)

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
                val sharedPref = getSharedPreferences("sound", Context.MODE_PRIVATE)
                val sound=sharedPref.getString("sound","sound1")

                when(sound){
                    "sound1" -> sound1?.start()
                    "sound2" -> sound2?.start()
                    "sound3" -> sound3?.start()
                    else -> sound1?.start()
                }
            }

        }

    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
    }

    override fun onDestroy() {
        super.onDestroy()
        mManager.unregisterListener(this)
    }
}
