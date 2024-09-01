package com.infoland.firstandroidprogram

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    lateinit var btnShow: Button
    lateinit var btnClear: Button
    lateinit var editName: EditText
    lateinit var txtSample: TextView
    lateinit var btnNext: Button

    lateinit var name: String

    // LAMBDA EXPRESSION -> assign function into a variable
    val confirmYES = { _:DialogInterface , _:Int ->
        intent = Intent(this, NextActivity::class.java)
        intent.putExtra("name", name)
        intent.putExtra("city", "Yangon")
        intent.putExtra("year", 2024)
        startActivity(intent)
        finish()
    }

    val confirmNO = { _:DialogInterface, _:Int ->
        Toast.makeText(this, "Clicked NO", Toast.LENGTH_LONG).show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        txtSample = findViewById(R.id.txt_sample)
        editName = findViewById(R.id.edit_name)
        btnShow = findViewById(R.id.btn_show)
        btnClear = findViewById(R.id.btn_clear)
        btnNext = findViewById(R.id.btn_next)

        btnShow.setOnClickListener {
            val name: String = editName.text.toString().trim()
            txtSample.text = if (name.length == 0) "Please enter the Name" else "Hello $name"
        }

        btnClear.setOnClickListener {
            editName.setText("")
            txtSample.text = "This is my sample text"
        }

        btnNext.setOnClickListener {
            name = editName.text.toString().trim()

            // VALIDATION -> COURSEWORK
            if (name.length == 0) {
                txtSample.text = "Please enter the Name"
            } else {
                // CONFIRMATION USING ALERTDIALOG -> COURSEWORK
                val alertDialog = AlertDialog.Builder(this)
                alertDialog.setTitle("Confirmation")
                alertDialog.setMessage("Are you sure you want to carry on?" +
                        "\nIs your Name $name?")
                alertDialog.setPositiveButton("YES", DialogInterface.OnClickListener(confirmYES))
                alertDialog.setNegativeButton("NO", DialogInterface.OnClickListener(confirmNO))
                alertDialog.setNeutralButton("Prefer Not to Say", null)
                alertDialog.show()
            }
        }
    } // on Create
}


//            Toast.makeText(this, "Show Button is clicked", Toast.LENGTH_LONG).show()
//            Log.i("Action Tag", "Show Button is clicked")