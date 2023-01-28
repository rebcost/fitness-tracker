package com.fcrysthian.myfitnesstraker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout

class MainActivity : AppCompatActivity() {

    private lateinit var buttonImc: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        buttonImc = findViewById(R.id.buttonImc)

        buttonImc.setOnClickListener{
            val intent = Intent(this, ImcActivity::class.java)
            startActivity(intent)
        }

    }
}