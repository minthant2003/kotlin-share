package com.infoland.recycleviewapplication

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

// HOW TO PLACE CARD VIEW ON RECYCLER VIEW -> USING ADAPTER
class StudentAdapter(var studentList: ArrayList<StudentModel>, var context: Context)
    : RecyclerView.Adapter<StudentAdapter.StudentViewHolder>()
{
    class StudentViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)
    {
        val studentName = itemView.findViewById<TextView>(R.id.txt_name)
        val studentBannerId = itemView.findViewById<TextView>(R.id.txt_bannerId)
        val btnDetails = itemView.findViewById<ImageButton>(R.id.btn_details)
        val btnEdit = itemView.findViewById<ImageButton>(R.id.btn_edit)
        val btnDelete = itemView.findViewById<ImageButton>(R.id.btn_delete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        val holder = LayoutInflater.from(parent.context).inflate(R.layout.student_item_view, parent, false)
        return StudentViewHolder(holder)
    }

    override fun getItemCount(): Int {
        return  studentList.size
    }

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        val currentStudent = studentList[position]

        holder.studentName.text = currentStudent.name
        holder.studentBannerId.text = currentStudent.bannerID

        holder.btnDetails.setOnClickListener {
            (context as? MainActivity)?.showStudentDetails(currentStudent)
        }

        holder.btnEdit.setOnClickListener {
            (context as? MainActivity)?.editStudent(currentStudent)
        }

        holder.btnDelete.setOnClickListener {
            (context as? MainActivity)?.deleteStudent(currentStudent.id) // if (context is Main) CODE BODY
        }
    }
}