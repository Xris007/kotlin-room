package com.noblecilla.tasks.model

import com.noblecilla.tasks.vo.Result

interface TaskDataSource {
    suspend fun all(): Result<List<Task>>
    suspend fun insert(task: Task): Result<Boolean>
    suspend fun update(task: Task): Result<Boolean>
    suspend fun delete(task: Task): Result<Boolean>
}
