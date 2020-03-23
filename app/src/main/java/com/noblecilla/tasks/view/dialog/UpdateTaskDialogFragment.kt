package com.noblecilla.tasks.view.dialog

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.noblecilla.tasks.databinding.FragmentTaskDialogBinding
import com.noblecilla.tasks.model.Task
import com.noblecilla.tasks.view.MainActivity
import com.noblecilla.tasks.viewmodel.TaskViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

/**
 * A simple [Fragment] subclass.
 * Use the [UpdateTaskDialogFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class UpdateTaskDialogFragment : BottomSheetDialogFragment() {

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

        arguments?.getParcelable<Task>(MainActivity.TASK_KEY)?.let {
            binding.taskDialogName.setText(it.name)
            binding.taskDialogName.requestFocus()
            binding.taskDone.setOnClickListener { _ ->
                taskViewModel.update(
                    Task(
                        it.id,
                        "${binding.taskDialogName.text}",
                        it.complete
                    )
                )
                dismiss()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment UpdateTaskDialogFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(task: Task) =
            UpdateTaskDialogFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(MainActivity.TASK_KEY, task)
                }
            }
    }
}
