package com.example.sensor

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.RadioGroup

class MusicActivity : AppCompatActivity() {

    val radioButton1=RadioButton(this)
    val radioButton2=RadioButton(this)
    val radioButton3=RadioButton(this)

    val radioGroup=RadioGroup(this)

    val linearLayout=findViewById<LinearLayout>(R.id.RadioGroup)

    val radioButton=findViewById<RadioButton>(R.id.radioButton)

    val id=radioGroup.checkedRadioButtonId

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_music)

        radioButton1.text="音源1"
        radioButton2.text="音源2"
        radioButton3.text="音源3"

        radioGroup.addView(radioButton1)
        radioGroup.addView(radioButton2)
        radioGroup.addView(radioButton3)

        linearLayout.addView(radioGroup)

        //最初に指定されているボタンの設定
        radioButton.isChecked=true

        //選択されているボタンのIDの取得

    }
}
