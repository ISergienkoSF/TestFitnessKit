package com.viol4tsf.testfitnesskit.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.viol4tsf.testfitnesskit.R
import com.viol4tsf.testfitnesskit.data.repository.Repository
import com.viol4tsf.testfitnesskit.databinding.ActivityMainBinding
import com.viol4tsf.testfitnesskit.ui.viewmodels.ScheduleViewModel
import com.viol4tsf.testfitnesskit.ui.viewmodels.ScheduleViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    lateinit var scheduleViewModel: ScheduleViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpViewModel()
    }

    private fun setUpViewModel(){

        val repository = Repository()

        val viewModelProviderFactory = ScheduleViewModelFactory(
            application,
            repository
        )

        scheduleViewModel = ViewModelProvider(
            this,
            viewModelProviderFactory
        ).get(ScheduleViewModel::class.java)
    }
}