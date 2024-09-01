package com.infoland.firstandroidprogram

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class NextActivity : AppCompatActivity() {
    lateinit var btnBack: Button
    lateinit var btnNext: Button

    private fun greeting(name: String?, city: String?, year: Int) {
        Log.i("Data Trace", "$name, $city, $year")
    }

    private fun backAction() {
        intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun nextAction() {
        intent = Intent(this, ImageActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_next)

        btnBack = findViewById(R.id.btnBack_Next)
        btnNext = findViewById(R.id.btnNext_Next)

        val bundle: Bundle? = intent.extras
        val name: String? = bundle?.getString("name")
        val city: String? = bundle?.getString("city")
        val year: Int = bundle!!.getInt("year")

        greeting(name, city, year)

        btnBack.setOnClickListener {
            backAction()
        }

        btnNext.setOnClickListener {
            nextAction()
        }
    }
}

//        Log.i("Data Trace", "$name, $city, $year")
//        val name: String? = intent.extras?.getString("name")