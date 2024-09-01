package com.infoland.recycleviewapplication

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class StudentDBHelper(context: Context): SQLiteOpenHelper(context, "student.db", null, 1) {
    private val tableName: String = "student"
    private val colId: String = "id"
    private val colBannerId: String = "banner_id"
    private val colName: String = "name"
    private val colEmail: String = "email"
    private val colSection: String = "section"
    private val colAY: String = "ay"

    override fun onCreate(db: SQLiteDatabase?) {
        val query = "CREATE TABLE $tableName(" +
                        "$colId integer PRIMARY KEY autoincrement," +
                        "$colBannerId text," +
                        "$colName text," +
                        "$colEmail text," +
                        "$colSection text," +
                        "$colAY text" +
                    ")"
        db?.execSQL(query)
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        val query = "DROP TABLE IF EXISTS $tableName"
        db?.execSQL(query)
        this.onCreate(db)
    }

    fun saveStudent(student: StudentModel): Long {
        val db = this.writableDatabase // CUD -> writable, R -> readable
        val values = ContentValues()

        values.put(colBannerId, student.bannerID)
        values.put(colName, student.name)
        values.put(colEmail, student.email)
        values.put(colSection, student.section)
        values.put(colAY, student.AY)

        val result = db.insert(tableName, null, values)
        db.close()

        return result
    }

    fun editStudent() {

    }

    fun deleteStudent() {

    }

    fun searchStudent(searchKey: String) {

    }

    fun getAllStudents(): ArrayList<StudentModel> {
        val db = this.readableDatabase //R -> readable, CUD -> writable
        val query = "SELECT * FROM $tableName"

        var studentList: ArrayList<StudentModel> = ArrayList<StudentModel>()

        var cursor: Cursor? = null // DEFINE CURSOR TO TRACK THE RECORD
        cursor = db.rawQuery(query, null)

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndexOrThrow(colId)) // TO PREVENT NULL POINTER -> TRY CATCH
                val bannerId = cursor.getString(cursor.getColumnIndexOrThrow(colBannerId))
                val name = cursor.getString(cursor.getColumnIndexOrThrow(colName))
                val email = cursor.getString(cursor.getColumnIndexOrThrow(colEmail))
                val section = cursor.getString(cursor.getColumnIndexOrThrow(colSection))
                val ay = cursor.getString(cursor.getColumnIndexOrThrow(colAY))

                val student = StudentModel(id, bannerId, name, email, section, ay)
                studentList.add(student)
            } while (cursor.moveToNext())
        }

        return  studentList
    }
}