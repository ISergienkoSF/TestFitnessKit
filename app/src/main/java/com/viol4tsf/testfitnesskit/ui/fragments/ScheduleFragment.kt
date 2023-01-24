package com.viol4tsf.testfitnesskit.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.viol4tsf.testfitnesskit.R
import com.viol4tsf.testfitnesskit.adapter.ScheduleAdapter
import com.viol4tsf.testfitnesskit.databinding.FragmentScheduleBinding
import com.viol4tsf.testfitnesskit.ui.MainActivity
import com.viol4tsf.testfitnesskit.ui.viewmodels.ScheduleViewModel


class ScheduleFragment : Fragment(R.layout.fragment_schedule) {

    private var _binding: FragmentScheduleBinding? = null
    private val binding get() = _binding!!
    lateinit var scheduleViewModel: ScheduleViewModel
    lateinit var scheduleAdapter: ScheduleAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        scheduleViewModel = (activity as MainActivity).scheduleViewModel

        setUpLessonsRecyclerView()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentScheduleBinding.inflate(
            inflater,
            container,
            false)

        return binding.root
    }

    private fun setUpLessonsRecyclerView() {
        scheduleAdapter = ScheduleAdapter()
        scheduleViewModel.getAllLessons()
        scheduleViewModel.lessonsList.observe(viewLifecycleOwner) { lessons ->
            lessons.body()?.let { scheduleAdapter.setList(it.lessons, it.trainers) }
        }
        binding.scheduleRecyclerView.apply {
            //создание двух столбцов в заметках
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            setHasFixedSize(true)
            adapter = scheduleAdapter
        }


    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}