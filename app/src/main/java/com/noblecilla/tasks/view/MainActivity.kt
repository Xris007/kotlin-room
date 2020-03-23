package com.noblecilla.tasks.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.noblecilla.tasks.R
import com.noblecilla.tasks.databinding.ActivityMainBinding
import com.noblecilla.tasks.model.Task
import com.noblecilla.tasks.view.adapter.TaskAdapter
import com.noblecilla.tasks.view.dialog.AddTaskDialogFragment
import com.noblecilla.tasks.view.dialog.UpdateTaskDialogFragment
import com.noblecilla.tasks.view.setting.Mode
import com.noblecilla.tasks.view.setting.Preferences
import com.noblecilla.tasks.view.utils.toast
import com.noblecilla.tasks.viewmodel.TaskViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val taskViewModel: TaskViewModel by viewModel()

    private lateinit var taskAdapter: TaskAdapter
    private lateinit var itemTouchHelper: ItemTouchHelper.SimpleCallback

    companion object {
        const val TASK_KEY = "task"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        binding.toolbar.title = getString(R.string.app_name)

        setupViewModel()
        setupView()
    }

    private fun setupViewModel() {
        taskViewModel.tasks.observe(this, tasks)
        taskViewModel.isSuccessful.observe(this, isSuccessful)
        taskViewModel.onMessageError.observe(this, onMessageError)
    }

    private val tasks = Observer<List<Task>> {
        taskAdapter.update(it)
    }

    private val isSuccessful = Observer<Boolean> {
        taskViewModel.all()
    }

    private val onMessageError = Observer<Any> {
        toast("$it")
    }

    private fun setupView() {
        itemTouchHelper = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                taskViewModel.tasks.value?.get(viewHolder.adapterPosition)
                    ?.let { taskViewModel.delete(it) }
            }
        }

        ItemTouchHelper(itemTouchHelper).attachToRecyclerView(binding.contentMain.taskRecycler)

        taskAdapter = TaskAdapter(emptyList(), { completeTask(it) }, { updateTaskDialog(it) })

        binding.contentMain.taskRecycler.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = taskAdapter
        }

        binding.contentMain.addTask.setOnClickListener { createTaskDialog() }
    }

    private fun createTaskDialog() {
        val taskDialogFragment = AddTaskDialogFragment()
        taskDialogFragment.show(supportFragmentManager, taskDialogFragment.tag)
    }

    private fun completeTask(task: Task) {
        task.complete?.let {
            if (it) {
                taskViewModel.update(Task(task.id, task.name, false))
            } else {
                taskViewModel.update(Task(task.id, task.name, true))
            }
        }
    }

    private fun updateTaskDialog(task: Task) {
        val taskDialogFragment = UpdateTaskDialogFragment.newInstance(task)
        taskDialogFragment.show(supportFragmentManager, taskDialogFragment.tag)
    }

    override fun onResume() {
        super.onResume()
        taskViewModel.all()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        when (Preferences.nightMode(this)) {
            Mode.LIGHT.ordinal -> menu?.findItem(R.id.night)?.isVisible = true
            Mode.NIGHT.ordinal -> menu?.findItem(R.id.light)?.isVisible = true
        }

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.light -> switchToMode(AppCompatDelegate.MODE_NIGHT_NO, Mode.LIGHT)
            R.id.night -> switchToMode(AppCompatDelegate.MODE_NIGHT_YES, Mode.NIGHT)
        }

        return super.onOptionsItemSelected(item)
    }

    private fun switchToMode(nightMode: Int, mode: Mode) {
        AppCompatDelegate.setDefaultNightMode(nightMode)
        Preferences.switchToMode(this, mode)
    }
}
