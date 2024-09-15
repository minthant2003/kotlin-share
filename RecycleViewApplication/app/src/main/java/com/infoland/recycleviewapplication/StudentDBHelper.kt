package com.infoland.recycleviewapplication

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

// CHANGE VERSION 1 TO 2 SINCE THE FOREIGN KEY IS ADDED TO THE TABLE
class StudentDBHelper(context: Context): SQLiteOpenHelper(context, "student.db", null, 2) {
    private val tableName: String = "student"
    private val colId: String = "id"
    private val colBannerId: String = "banner_id"
    private val colName: String = "name"
    private val colEmail: String = "email"
    private val colSection: String = "section"
    private val colAY: String = "ay"

    // ANOTHER TABLE
    private val tableNameQlf = "qualification"
    private val colTitleQlf = "title"
    private val colYearQlf = "year"
    private val colStudentId = "std_id"

    // MIGRATION
    override fun onCreate(db: SQLiteDatabase?) {
        //FIRST TABLE
        val query = "CREATE TABLE $tableName(" +
                        "$colId integer PRIMARY KEY autoincrement," +
                        "$colBannerId text," +
                        "$colName text," +
                        "$colEmail text," +
                        "$colSection text," +
                        "$colAY text" +
                    ")"
        db?.execSQL(query)

        // SECOND TABLE
        val queryQlf = "CREATE TABLE $tableNameQlf($colId integer primary key autoincrement, " +
                    "$colTitleQlf text, " +
                    "$colYearQlf text, " +
                    "$colStudentId integer, " +
                    "foreign key($colStudentId) references $tableName($colId)" +
                    "ON DELETE CASCADE" +
                ")"
        db?.execSQL(queryQlf)
    }

    // TABLE MODIFICATION
    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        //FIRST TABLE
        val query = "DROP TABLE IF EXISTS $tableName"
        db?.execSQL(query)

        // SECOND TABLE
        val queryQlf = "DROP TABLE IF EXISTS $tableNameQlf"
        db?.execSQL(queryQlf)

        this.onCreate(db)
    }

    // FOR FOREIGN KEY -> SQLite
    override fun onOpen(db: SQLiteDatabase?) {
        super.onOpen(db)
        val query = "PRAGMA foreign_keys=ON"
        db?.execSQL(query)
    }

    // CREATE
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

    // UPDATE
    fun editStudent(id: Int, student: StudentModel): Int {
        val db = this.writableDatabase // CUD -> writable, R -> readable
        val values = ContentValues()

        values.put(colBannerId, student.bannerID)
        values.put(colName, student.name)
        values.put(colEmail, student.email)
        values.put(colSection, student.section)
        values.put(colAY, student.AY)

        // CHECK ID FOR UPDATE
        val result = db.update(tableName, values, "$colId=$id", null)
        db.close()

        return result
    }

    // DELETE
    fun deleteStudent(id: Int): Int {
        val db = this.writableDatabase

        val result = db.delete(tableName, "$colId=$id", null)
        db.close()

        return result
    }

    fun searchStudent(searchKey: String): ArrayList<StudentModel> {
        val db = this.readableDatabase //R -> readable, CUD -> writable
        val query = "SELECT * FROM $tableName" +
                    " WHERE $colEmail LIKE '%$searchKey%'" +
                    " OR $colName LIKE '%$searchKey%'" +
                    " OR $colSection LIKE '%$searchKey%'" +
                    " OR $colAY LIKE '%$searchKey%'"

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

//    FOR QUALIFICATION TABLE
    fun addQualification(qualification: Qualification): Long {
        val db = this.writableDatabase // CUD -> writable, R -> readable
        val values = ContentValues()

        values.put(colTitleQlf, qualification.title)
        values.put(colYearQlf, qualification.year)
        values.put(colStudentId, qualification.stdId)

        val result = db.insert(tableNameQlf, null, values)
        db.close()

        return result
    }

    fun getQualification(stdId: Int): ArrayList<Qualification> {
        val db = this.readableDatabase //R -> readable, CUD -> writable
        val query = "SELECT * FROM $tableNameQlf WHERE $colStudentId=$stdId"

        var qlfList: ArrayList<Qualification> = ArrayList<Qualification>()

        var cursor: Cursor? = null // DEFINE CURSOR TO TRACK THE RECORD
        cursor = db.rawQuery(query, null)

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndexOrThrow(colId)) // TO PREVENT NULL POINTER -> TRY CATCH
                val qlfTitle = cursor.getString(cursor.getColumnIndexOrThrow(colTitleQlf))
                val qlfYear = cursor.getString(cursor.getColumnIndexOrThrow(colYearQlf))
                val stdId = cursor.getInt(cursor.getColumnIndexOrThrow(colStudentId))

                val qualification = Qualification(id, qlfTitle, qlfYear, stdId)
                qlfList.add(qualification)
            } while (cursor.moveToNext())
        }

        return qlfList
    }
}