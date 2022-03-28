package com.example.student_management


import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.student_management.database.Student_Database
import com.example.student_management.model.Student_Entity
import kotlinx.android.synthetic.main.activity_first_fragment.*
import kotlinx.android.synthetic.main.activity_first_fragment.edt_grade
import kotlinx.android.synthetic.main.activity_first_fragment.edt_name

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class new_activity : AppCompatActivity(), CoroutineScope {
    private var noteDB: Student_Database ?= null

    private lateinit var mJob: Job
    override val coroutineContext: CoroutineContext
        get() = mJob + Dispatchers.Main


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first_fragment)
        supportActionBar?.setTitle("Thêm Sinh viên")
        mJob = Job()
        noteDB = Student_Database.getDatabase(this)
material_timepicker_cancel_button.setOnClickListener {

        val intent = Intent(this,MainActivity::class.java)
        startActivity(intent)
    }

        btn_add.setOnClickListener {
            launch {
                val strName: String = edt_name.text.toString()
                val strGrade: String = edt_grade.text.toString()
                val strEmail: String = edt_email.text.toString()
                val strAddress: String = edt_address.text.toString()
                if (strName =="" && strGrade == "" && strEmail=="" && strAddress=="" ) {
                    Toast.makeText(this@new_activity,"Bạn phải nhập đầy đủ thông tin",Toast.LENGTH_SHORT).show()

                } else if (strName =="" || strGrade == ""|| strEmail == ""|| strAddress == "") {
                   Toast.makeText(this@new_activity,"Bạn phải nhập đầy đủ thông tin",Toast.LENGTH_SHORT).show()

                } else {
                    noteDB?.studentDao()?.insert(Student_Entity(name = strName, grade = strGrade,email = strEmail,address = strAddress))
                    Toast.makeText(this@new_activity,"Thêm Thành Công",Toast.LENGTH_SHORT).show()

                    finish()
                }

        }
}

}
}