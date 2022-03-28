package com.example.student_management.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.student_management.model.Student_Entity

@Database(entities = [Student_Entity::class], version=1)
abstract class Student_Database: RoomDatabase(){
    abstract fun studentDao():Student
    companion object{
        private var INSTANCE: Student_Database?= null
        private val DB_NAME = "student_manager_db"
        fun getDatabase(context: Context): Student_Database {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    Student_Database::class.java,
                    DB_NAME
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}