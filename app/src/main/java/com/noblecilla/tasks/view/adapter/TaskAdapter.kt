package com.noblecilla.tasks.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.noblecilla.tasks.databinding.LayoutTaskBinding
import com.noblecilla.tasks.model.Task
import com.noblecilla.tasks.view.utils.complete

class TaskAdapter(
    private var list: List<Task>,
    private val complete: (Task) -> Unit,
    private val update: (Task) -> Unit
) : RecyclerView.Adapter<TaskAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = LayoutTaskBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position], complete, update)
    }

    override fun getItemCount() = list.size

    fun update(list: List<Task>) {
        this.list = list
        notifyDataSetChanged()
    }

    class ViewHolder(private val binding: LayoutTaskBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            task: Task,
            complete: (Task) -> Unit,
            update: (Task) -> Unit
        ) = with(itemView) {
            binding.taskName.text = task.name
            task.complete?.let {
                binding.taskComplete.isChecked = it
                binding.taskName.complete(it)
            }

            binding.taskComplete.setOnClickListener { complete(task) }
            setOnClickListener { update(task) }
        }
    }
}
