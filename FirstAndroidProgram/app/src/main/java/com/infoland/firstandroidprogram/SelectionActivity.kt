package com.infoland.firstandroidprogram

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.TimeZone

class SelectionActivity : AppCompatActivity() {
    lateinit var studentEmail : EditText
    lateinit var section : RadioGroup
    lateinit var checkMusicClub : CheckBox
    lateinit var checkFusalClub : CheckBox
    lateinit var checkSportClub : CheckBox
    lateinit var spinner : Spinner
    lateinit var btnDoBPicker : Button
    lateinit var txtDoB : TextView
    lateinit var btnTimePicker : Button
    lateinit var txtTime : TextView
    lateinit var btnSubmit : Button
    lateinit var btnClear : Button

    val campusList = listOf("Thailand", "Myanmar", "Japan", "Vietnam", "Singapore") // FOR SPINNER
    var selectedCampus = ""

    // DATE FORMAT AND TIME FORMAT
    val dateFormat = SimpleDateFormat("dd MMM, YYYY")
    val timeFormat = SimpleDateFormat("hh:mm a")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_selection)

        studentEmail = findViewById(R.id.student_email)

        section = findViewById(R.id.rdo_group_section)

        checkMusicClub = findViewById(R.id.check_music)
        checkFusalClub = findViewById(R.id.check_fusal)
        checkSportClub = findViewById(R.id.check_sport)

        spinner = findViewById(R.id.spinner_campus)

        btnDoBPicker = findViewById(R.id.btn_select_DoB)
        txtDoB = findViewById(R.id.txt_select_DoB)
        btnTimePicker = findViewById(R.id.btn_select_time)
        txtTime = findViewById(R.id.txt_select_time)
        btnSubmit = findViewById(R.id.btn_select_submit)
        btnClear = findViewById(R.id.btn_select_clear)

        // LOAD SPINNER START
        val sortedCampusList = campusList.sortedBy { it } // SORT THE ARRAY

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, sortedCampusList)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
        // LOAD SPINNER END

        spinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                selectedCampus = sortedCampusList[p2]
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }

        btnDoBPicker.setOnClickListener{
            showDatePicker()
        }
        btnTimePicker.setOnClickListener{
            showTimePicker()
        }
        btnSubmit.setOnClickListener{
            submitInfo()
        }
        btnClear.setOnClickListener{
            clearInfo()
        }
    } // ON CREATE

    private fun clearInfo() {
        studentEmail.setText("")
        section.clearCheck()
        checkMusicClub.isChecked = false
        checkFusalClub.isChecked = false
        checkSportClub.isChecked = false
        selectedCampus = ""
        txtDoB.text = ""
        txtTime.text = ""
    }

    private fun submitInfo() {
        val strBuilder = StringBuilder()

        val email = studentEmail.text.toString()
        val radioId = section.checkedRadioButtonId
        val DoB = txtDoB.text.toString()
        val time = txtTime.text.toString()

        // EMAIL VALIDATION
        if (email.isNotEmpty()) {
            strBuilder.append("Email : $email\n")
        } else {
            strBuilder.append("Email : Empty\n")
        }

        // VALIDATION FOR RADIO BUTTON
        if (radioId == -1) {
            strBuilder.append("Selected Section : No Section Selected\n")
        } else {
            val radioBtn = findViewById<RadioButton>(radioId)
            strBuilder.append("Selected Section : ${radioBtn.text}\n")
        }

        // VALIDATION FOR CHECK BOX
        var isCheckFlag: Boolean = false
        strBuilder.append("Selected Club : ")
        if (checkMusicClub.isChecked) {
            isCheckFlag = true
            strBuilder.append("${checkMusicClub.text}\n")
        }
        if (checkFusalClub.isChecked) {
            isCheckFlag = true
            strBuilder.append("${checkFusalClub.text}\n")
        }
        if (checkSportClub.isChecked) {
            isCheckFlag = true
            strBuilder.append("${checkSportClub.text}\n")
        }
        if (!isCheckFlag) strBuilder.append("No Club selected\n")

        strBuilder.append("Selected Campus : $selectedCampus\n")
        strBuilder.append("Selected DoB : $DoB\n")
        strBuilder.append("Selected Time : $time\n")

        val alertDialog = AlertDialog.Builder(this)
        alertDialog.setTitle("Sudent Information")
        alertDialog.setMessage(strBuilder.toString())
        alertDialog.setCancelable(false)
        alertDialog.setNegativeButton("OK") {
            dialog, _ -> dialog.dismiss()
        }
        alertDialog.show()
    }

    // TIME PICKER DIALOG STILL NOT CORRECT
    private fun showTimePicker() {
        val calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))

        TimePickerDialog(this, {
                _, selectedHour, selectedMin ->
                txtTime.text = "$selectedHour:$selectedMin"
            }, calendar.get(Calendar.HOUR), calendar.get(Calendar.MINUTE), false
        ).show()
    } // TIME PICKER

    // DATE PICKER DIALOG STILL NOT CORRECT
    private fun showDatePicker() {
        val calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))

        DatePickerDialog(this, {
                _, selectedYear, selectedMonth, selectedDay ->
                txtDoB.text = "$selectedDay-" + (selectedMonth + 1) + "-$selectedYear"
            },calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)
        ).show()
    } // DATE PICKER
} // END OF CLASS