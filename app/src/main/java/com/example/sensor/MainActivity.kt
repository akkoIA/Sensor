package com.example.sensor

import android.content.Context
import android.hardware.*
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.properties.Delegates

class MainActivity : AppCompatActivity(),SensorEventListener{
    private var mManager: SensorManager by Delegates.notNull<SensorManager>()
    private var mSensor: Sensor by Delegates.notNull<Sensor>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        mManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        //その他のセンサーを取得する場合には引数を違うものに変更する
        mSensor = mManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)



        val sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        val sensor: Sensor? = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)





        sensorManager.registerListener()
    }

    override fun onSensorChanged(event: SensorEvent?) {
        val text = findViewById<TextView>(R.id.sensor)

        //3つの値が配列で入ってくる
        text("nullpo", "______")
        //X軸方法
        text("nullpo", event?.values!![0].toString())
        //Y軸方法
        text("nullpo", event?.values!![1].toString())
        //Z軸方法
        text("nullpo", event?.values!![2].toString())
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
