package com.infoland.recycleviewapplication

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.text.Layout
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    lateinit var editSearchKey: EditText
    lateinit var btnSearchKey: Button
    lateinit var recyclerView: RecyclerView
    lateinit var btnAdd: FloatingActionButton

    var studentList = ArrayList<StudentModel>()
    var dbHelper = StudentDBHelper(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editSearchKey = findViewById<EditText>(R.id.edit_search_key)
        btnSearchKey = findViewById<Button>(R.id.btn_search_key)
        recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        btnAdd = findViewById<FloatingActionButton>(R.id.btn_add)

        studentList = dbHelper.getAllStudents()

        btnSearchKey.setOnClickListener {
            searchStudent(editSearchKey.text.toString())
        }

        btnAdd.setOnClickListener {
            addStudent()
        }

        if (studentList.size > 0) {
            recyclerView.visibility = View.VISIBLE
            this.showRecyclerView()
        } else {
            recyclerView.visibility = View.GONE
        }
    }

    // SHOW RECYCLER VIEW -> AT THE BEGINNING
    private fun showRecyclerView() {
        studentList = dbHelper.getAllStudents()
        // INITIALISE THE RECYCLER VIEW
        recyclerView.layoutManager = LinearLayoutManager(this)
        val studentAdapter = StudentAdapter(studentList, this)
        studentAdapter.notifyDataSetChanged()

        recyclerView.adapter = studentAdapter
    }

    // SHOW RECYCLER VIEW -> AFTER SEARCH
    private fun searchView(studentList: ArrayList<StudentModel>) {
        // INITIALISE THE RECYCLER VIEW
        recyclerView.layoutManager = LinearLayoutManager(this)
        val studentAdapter = StudentAdapter(studentList, this)
        studentAdapter.notifyDataSetChanged()

        recyclerView.adapter = studentAdapter
    }

    // SHOW STUDENT DETAILS INFORMATION
    fun showStudentDetails(student: StudentModel) {
        val stBuilder = StringBuilder()
        stBuilder.append("Banner ID : ${student.bannerID}\n")
        stBuilder.append("Name : ${student.name}\n")
        stBuilder.append("Email : ${student.email}\n")
        stBuilder.append("Section : ${student.section}\n")
        stBuilder.append("AY : ${student.AY}")

        val alertDialog = AlertDialog.Builder(this)
        alertDialog.setTitle("Student Information")
        alertDialog.setMessage(stBuilder.toString())
        alertDialog.setCancelable(false)
        alertDialog.setPositiveButton("OK") { dialogInterface, _ ->
            dialogInterface.dismiss()
        }
        alertDialog.show()
    }

    private fun searchStudent(key: String) {
        var studentList = ArrayList<StudentModel>()
        studentList = dbHelper.searchStudent(key)

        if (studentList.size > 0) {
            recyclerView.visibility = View.VISIBLE
            searchView(studentList) // UPDATE RECYCLER VIEW
        } else {
            recyclerView.visibility = View.GONE
            Toast.makeText(this@MainActivity, "No data Found!", Toast.LENGTH_LONG).show()
        }
    }

    // ADD STUDENT
    private fun addStudent() {
        val dialog = Dialog(this)
        dialog.setTitle("Add Student")
        dialog.setContentView(R.layout.add_student_dialog_view)
        dialog.setCancelable(false)

        val editBannerId = dialog.findViewById<EditText>(R.id.edit_banner_id)
        val editName = dialog.findViewById<EditText>(R.id.edit_name)
        val editEmail = dialog.findViewById<EditText>(R.id.edit_email)
        val editSection = dialog.findViewById<EditText>(R.id.edit_section)
        val editAY = dialog.findViewById<EditText>(R.id.edit_ay)
        val btnSave = dialog.findViewById<Button>(R.id.btn_save_student)
        val btnCancel = dialog.findViewById<Button>(R.id.btn_cancel_student)

        dialog.show()

        btnSave.setOnClickListener {
            val student = StudentModel(0, editBannerId.text.toString(), editName.text.toString(),
                                        editEmail.text.toString(), editSection.text.toString(), editAY.text.toString())
            val result = dbHelper.saveStudent(student)
            dialog.dismiss()

            showRecyclerView()

            if (result != (-1).toLong()) {
                Toast.makeText(this, "Student data has been saved.", Toast.LENGTH_LONG).show()
            }
        }
        btnCancel.setOnClickListener {
            dialog.dismiss()
        }
    }

    // EDIT STUDENT
    fun editStudent(student: StudentModel) {
        val dialog = Dialog(this)
        dialog.setTitle("Add Student")
        dialog.setContentView(R.layout.add_student_dialog_view)
        dialog.setCancelable(false)

        val editBannerId = dialog.findViewById<EditText>(R.id.edit_banner_id)
        val editName = dialog.findViewById<EditText>(R.id.edit_name)
        val editEmail = dialog.findViewById<EditText>(R.id.edit_email)
        val editSection = dialog.findViewById<EditText>(R.id.edit_section)
        val editAY = dialog.findViewById<EditText>(R.id.edit_ay)
        val btnEdit = dialog.findViewById<Button>(R.id.btn_save_student)
        val btnCancel = dialog.findViewById<Button>(R.id.btn_cancel_student)

        btnEdit.text = "Edit" // CHANGE BUTTON TEXT FROM SAVE TO EDIT
        editBannerId.setText(student.bannerID)
        editName.setText(student.name)
        editEmail.setText(student.email)
        editSection.setText(student.section)
        editAY.setText(student.AY)

        dialog.show()

        btnEdit.setOnClickListener {
            val newStudent = StudentModel(
                                student.id,
                                editBannerId.text.toString(),
                                editName.text.toString(),
                                editEmail.text.toString(),
                                editSection.text.toString(),
                                editAY.text.toString()
                            )

            val result = dbHelper.editStudent(student.id, newStudent)

            dialog.dismiss()
            showRecyclerView()

            if (result != -1) {
                Toast.makeText(this, "Student data has been edited.", Toast.LENGTH_LONG).show()
            }
        }
        btnCancel.setOnClickListener {
            dialog.dismiss()
        }
    }

    // DELETE STUDENT
    fun deleteStudent(id: Int) {
        val alertDialog = AlertDialog.Builder(this)
        alertDialog.setTitle("Confirmation")
        alertDialog.setMessage("Are you sure you want to delete it?")
        alertDialog.setCancelable(false)
        alertDialog.setPositiveButton("YES") { dialog, _ ->
            val result = dbHelper.deleteStudent(id)
            if (result != -1) {
                Toast.makeText(this@MainActivity, "I AM DELETED", Toast.LENGTH_LONG).show()
                showRecyclerView()
            }
        }
        alertDialog.setNegativeButton("NO") { dialog, _ ->
            dialog.dismiss()
        }
        alertDialog.show() //SHOW THE DIALOG
    }
}

//        studentList.add(StudentModel("1627", "minthant", "min@gmail.com", "BC18", "2023-2024"))
//        studentList.add(StudentModel("7165", "shinpaing", "shinpaing@gmail.com", "BC19", "2022-2024"))
//        studentList.add(StudentModel("0987","hein","hein@gmail.com","BC11","2023-2021"))
//        studentList.add(StudentModel("1985", "naing", "naing@gmail.com", "BC14", "2028-2020"))
//        studentList.add(StudentModel("0917", "paing", "paing@gmail.com", "BC17", "2021-2025"))
//        studentList.add(StudentModel("1627", "minthant", "min@gmail.com", "BC18", "2023-2024"))
//        studentList.add(StudentModel("7165", "shinpaing", "shinpaing@gmail.com", "BC19", "2022-2024"))
//        studentList.add(StudentModel("0987","hein","hein@gmail.com","BC11","2023-2021"))
//        studentList.add(StudentModel("1985", "naing", "naing@gmail.com", "BC14", "2028-2020"))
//        studentList.add(StudentModel("0917", "paing", "paing@gmail.com", "BC17", "2021-2025"))