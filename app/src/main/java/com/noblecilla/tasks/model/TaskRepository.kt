package com.noblecilla.tasks.model

import android.content.Context
import com.noblecilla.tasks.data.TaskDao
import com.noblecilla.tasks.data.TaskDatabase
import com.noblecilla.tasks.vo.Result

class TaskRepository(context: Context) : TaskDataSource {

    private lateinit var taskDao: TaskDao

    init {
        val db = TaskDatabase.getInstance(context)
        db?.let { taskDao = it.taskDao() }
    }

    override suspend fun all(): Result<List<Task>> {
        return try {
            Result.Success(taskDao.all())
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun insert(task: Task): Result<Boolean> {
        return try {
            taskDao.insert(task)
            Result.Success(true)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun update(task: Task): Result<Boolean> {
        return try {
            taskDao.update(task)
            Result.Success(true)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun delete(task: Task): Result<Boolean> {
        return try {
            taskDao.delete(task)
            Result.Success(true)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}
