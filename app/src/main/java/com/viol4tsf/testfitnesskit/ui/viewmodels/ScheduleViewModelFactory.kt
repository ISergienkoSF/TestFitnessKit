package com.viol4tsf.testfitnesskit.ui.viewmodels

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.viol4tsf.testfitnesskit.data.repository.Repository

class ScheduleViewModelFactory(
    val app: Application,
    private val repository: Repository
): ViewModelProvider.Factory  {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ScheduleViewModel(app, repository) as T
    }
}