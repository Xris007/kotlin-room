package com.noblecilla.tasks.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "task")
data class Task(
    @PrimaryKey(autoGenerate = true) val id: Int?,
    val name: String?,
    val complete: Boolean?
) : Parcelable
