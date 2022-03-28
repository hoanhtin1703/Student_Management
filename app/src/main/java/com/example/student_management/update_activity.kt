package com.example.student_management

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.student_management.database.Student_Database
import com.example.student_management.model.Student_Entity
import kotlinx.android.synthetic.main.activity_first_fragment.*
import kotlinx.android.synthetic.main.activity_fragment2.*
import kotlinx.android.synthetic.main.activity_fragment2.edt_address
import kotlinx.android.synthetic.main.activity_fragment2.edt_email
import kotlinx.android.synthetic.main.activity_fragment2.edt_grade
import kotlinx.android.synthetic.main.activity_fragment2.edt_name
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext


class update_activity(
) : AppCompatActivity(),CoroutineScope {


    private var noteDB: Student_Database ?= null

    private lateinit var mJob: Job
    override val coroutineContext: CoroutineContext
        get() = mJob + Dispatchers.Main



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.setTitle("Chỉnh sửa thông tin sinh viên");
        setContentView(R.layout.activity_fragment2)
        mJob = Job()
        noteDB = Student_Database.getDatabase(this)
//      var notes = emptyList<Student_Entity>()

        val id:Int = Integer.parseInt(intent.extras?.get("student_id").toString())
        val name: String = intent.extras?.get("student_name").toString()
        val grade: String = intent.extras?.get("student_grade").toString()
        val email: String = intent.extras?.get("student_email").toString()
        val address: String = intent.extras?.get("student_address").toString()
//        var strUser: String? = intent.getStringExtra("student_name") // 2
        edt_name.setText(name)
        edt_grade.setText(grade)
        edt_email.setText(email)
        edt_address.setText(address)
        btn_update.setOnClickListener{
            launch {
                val strname: String = edt_name.text.toString()
                val strgrade: String = edt_grade.text.toString()
                val strEmail: String = edt_email.text.toString()
                val strAddress: String = edt_address.text.toString()
                if (strname =="" || strgrade == "") {
                    Toast.makeText(this@update_activity,"Bạn phải nhập đầy đủ thông tin",Toast.LENGTH_SHORT).show()
                } else {
                    noteDB?.studentDao()?.update(Student_Entity(id = id,name=strname,grade = strgrade,email=strEmail,address=strAddress))
                    Toast.makeText(this@update_activity,"Cập Nhật Thành Công",Toast.LENGTH_SHORT).show()
                    val intent = Intent()
                    setResult(RESULT_OK,intent)
                    finish()
                }

            }
        }
        cancel_button.setOnClickListener {
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }
    }


}