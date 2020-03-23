package com.noblecilla.tasks.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.noblecilla.tasks.model.Task

@Database(entities = [Task::class], version = 1, exportSchema = false)
abstract class TaskDatabase : RoomDatabase() {

    abstract fun taskDao(): TaskDao

    companion object {
        private var INSTANCE: TaskDatabase? = null
        private const val DB_NAME = "TaskDb.db"

        fun getInstance(context: Context): TaskDatabase? {
            if (INSTANCE == null) {
                synchronized(TaskDatabase::class) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        TaskDatabase::class.java,
                        DB_NAME
                    ).build()
                }
            }
            return INSTANCE
        }
    }
}
