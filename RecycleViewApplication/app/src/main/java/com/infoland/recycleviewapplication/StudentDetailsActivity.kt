package com.infoland.recycleviewapplication

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.BundleCompat

// RECEIVE THE DATA FROM THE MAIN IN CLASS FORMAT USING PARCELABLE WITH BUNDLE
class StudentDetailsActivity : AppCompatActivity() {
//    lateinit var txtStudentDetails: TextView
    lateinit var txtStudentName: TextView
    lateinit var txtStudentBannerId: TextView
    lateinit var txtStudentEmail: TextView
    lateinit var txtStudentSection: TextView
    lateinit var txtStudentAY: TextView

    lateinit var btnAddQlf: Button
    lateinit var btnshowQlf: Button

    var dbHelper = StudentDBHelper(this@StudentDetailsActivity)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_details)

//        txtStudentDetails = findViewById(R.id.text_student_details)
        txtStudentName = findViewById(R.id.txt_student_name)
        txtStudentBannerId = findViewById(R.id.txt_student_bannerId)
        txtStudentEmail = findViewById(R.id.txt_student_email)
        txtStudentSection = findViewById(R.id.txt_studnet_section)
        txtStudentAY = findViewById(R.id.txt_student_ay)

        btnAddQlf = findViewById(R.id.btn_add_qualification)
        btnshowQlf = findViewById(R.id.btn_show_qualification)

//        val bundle: Bundle? = intent.extras
//        val details = bundle?.getString("details")
//        txtStudentDetails.text = details

        // RECEIVE DATA FROM MAIN IN BUNDLE PARCELABLE FORMAT
        val bundle: Bundle? = intent.extras
        val student: StudentModel? = BundleCompat.getParcelable(bundle!!, "StudentModel", StudentModel::class.java)

        txtStudentName.text = student?.name
        txtStudentBannerId.text = student?.bannerID
        txtStudentEmail.text = student?.email
        txtStudentSection.text = student?.section
        txtStudentAY.text = student?.AY

        btnAddQlf.setOnClickListener() {
            addQualification(student!!.id)
        }
        btnshowQlf.setOnClickListener() {
            showQualification(student!!.id)
        }
    }

    private fun showQualification(stdId: Int) {
        var qlfList = ArrayList<Qualification>()

        qlfList = this.dbHelper.getQualification(stdId)

        if (qlfList.size == 0) {
            Toast.makeText(this@StudentDetailsActivity, "No Qualification", Toast.LENGTH_LONG).show()
        } else {
            val stBuilder = StringBuilder()

            for (i in 0 until qlfList.size) {
                stBuilder.append("Title : ${qlfList[i].title}\n")
                stBuilder.append("Year : ${qlfList[i].year}\n\n")
            }

            val alertDialog = AlertDialog.Builder(this)
            alertDialog.setTitle("Student Information")
            alertDialog.setMessage(stBuilder.toString())
            alertDialog.setCancelable(false)

            alertDialog.setPositiveButton("OK") { dialogInterface, _ ->
                dialogInterface.dismiss()
            }
            alertDialog.show()
        }
    }

    private fun addQualification(stdId: Int) {
        val dialog = Dialog(this@StudentDetailsActivity)
        dialog.setTitle("Add Qualification")
        dialog.setContentView(R.layout.add_qualification_dialog_view)
        dialog.setCancelable(false)

        val editQlfTitle = dialog.findViewById<EditText>(R.id.edit_qualification_title)
        val editQlfYear = dialog.findViewById<EditText>(R.id.edit_qualification_year)
        val btnSave = dialog.findViewById<Button>(R.id.btn_qualification_save)
        val btnCancel = dialog.findViewById<Button>(R.id.btn_qualification_cancel)

        dialog.show()

        btnSave.setOnClickListener {
            val qualification = Qualification(0, editQlfTitle.text.toString(), editQlfYear.text.toString(), stdId)
            val result = dbHelper.addQualification(qualification)
            dialog.dismiss()

            if (result != (-1).toLong()) {
                Toast.makeText(this, "Qualification data has been saved.", Toast.LENGTH_LONG).show()
            }
        }
        btnCancel.setOnClickListener {
            dialog.dismiss()
        }
    }
}