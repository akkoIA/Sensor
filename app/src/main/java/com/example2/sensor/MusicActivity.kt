package com.example2.sensor

import android.content.Context
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.RadioButton
import android.widget.RadioGroup
import kotlinx.android.synthetic.main.activity_music.*

class MusicActivity : AppCompatActivity() {

    var sound1: MediaPlayer? = null
    var sound2: MediaPlayer? = null
    var sound3: MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_music)

        val radioGroup=findViewById<RadioGroup>(R.id.RadioGroup)

        val radioButton1=findViewById<RadioButton>(R.id.radioButton)
        val radioButton2=findViewById<RadioButton>(R.id.radioButton2)
        val radioButton3=findViewById<RadioButton>(R.id.radioButton3)

        sound1=MediaPlayer.create(this,R.raw.doamusic)
        sound2=MediaPlayer.create(this,R.raw.siren)
        sound3=MediaPlayer.create(this,R.raw.god)


        radioGroup.setOnCheckedChangeListener { group, checkedId ->
            when(checkedId){
                R.id.radioButton -> sound1?.start()
                R.id.radioButton2 -> sound2?.start()
                R.id.radioButton3 -> sound3?.start()
            }
        }

        floatingActionButton4.setOnClickListener{
            val sharedPref = getSharedPreferences("sound", Context.MODE_PRIVATE)
            val temp = when(radioGroup.checkedRadioButtonId){
                R.id.radioButton ->"sound1"
                R.id.radioButton2 ->"sound2"
                R.id.radioButton3 ->"sound3"
                else -> "sound1"
            }

            sharedPref.edit().putString("sound", temp).apply()

            finish()
        }


    }
}
