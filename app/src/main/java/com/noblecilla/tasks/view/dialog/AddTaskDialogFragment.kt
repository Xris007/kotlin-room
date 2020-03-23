package com.noblecilla.tasks.view.dialog

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.noblecilla.tasks.databinding.FragmentTaskDialogBinding
import com.noblecilla.tasks.model.Task
import com.noblecilla.tasks.viewmodel.TaskViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

/**
 * A simple [Fragment] subclass.
 */
class AddTaskDialogFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentTaskDialogBinding? = null
    private val binding
        get() = _binding!!

    private val taskViewModel: TaskViewModel by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentTaskDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.taskDialogName.requestFocus()
        binding.taskDone.setOnClickListener {
            taskViewModel.insert(
                Task(
                    null,
                    "${binding.taskDialogName.text}",
                    false
                )
            )
            dismiss()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
