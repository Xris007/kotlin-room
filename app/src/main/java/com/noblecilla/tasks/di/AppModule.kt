package com.noblecilla.tasks.di

import com.noblecilla.tasks.model.TaskDataSource
import com.noblecilla.tasks.model.TaskRepository
import com.noblecilla.tasks.viewmodel.TaskViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single<TaskDataSource> { TaskRepository(androidContext()) }
    viewModel { TaskViewModel(get()) }
}
