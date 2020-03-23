package com.noblecilla.tasks.data

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import com.noblecilla.tasks.model.Task

@Dao
interface TaskDao {

    @Query("select * from task order by complete")
    suspend fun all(): List<Task>

    @Insert(onConflict = REPLACE)
    suspend fun insert(task: Task)

    @Update(onConflict = REPLACE)
    suspend fun update(task: Task)

    @Delete
    suspend fun delete(task: Task)
}
