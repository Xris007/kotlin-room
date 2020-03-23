package com.noblecilla.tasks.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.noblecilla.tasks.model.Task
import com.noblecilla.tasks.model.TaskDataSource
import com.noblecilla.tasks.vo.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TaskViewModel(private val dataSource: TaskDataSource) : ViewModel() {

    private val _tasks = MutableLiveData<List<Task>>()
    val tasks: LiveData<List<Task>> = _tasks

    private val _isSuccessful = MutableLiveData<Boolean>()
    val isSuccessful: LiveData<Boolean> = _isSuccessful

    private val _onMessageError = MutableLiveData<Any>()
    val onMessageError: LiveData<Any> = _onMessageError

    fun all() = viewModelScope.launch {
        val result: Result<List<Task>> = withContext(Dispatchers.IO) {
            dataSource.all()
        }

        when (result) {
            is Result.Success -> _tasks.value = result.data
            is Result.Error -> _onMessageError.value = result.exception
        }
    }

    fun insert(task: Task) = viewModelScope.launch {
        delay(250)
        val result: Result<Boolean> = withContext(Dispatchers.IO) {
            dataSource.insert(task)
        }

        when (result) {
            is Result.Success -> _isSuccessful.value = result.data
            is Result.Error -> _onMessageError.value = result.exception
        }
    }

    fun update(task: Task) = viewModelScope.launch {
        delay(250)
        val result: Result<Boolean> = withContext(Dispatchers.IO) {
            dataSource.update(task)
        }

        when (result) {
            is Result.Success -> _isSuccessful.value = result.data
            is Result.Error -> _onMessageError.value = result.exception
        }
    }

    fun delete(task: Task) = viewModelScope.launch {
        delay(250)
        val result: Result<Boolean> = withContext(Dispatchers.IO) {
            dataSource.delete(task)
        }

        when (result) {
            is Result.Success -> _isSuccessful.value = result.data
            is Result.Error -> _onMessageError.value = result.exception
        }
    }
}
