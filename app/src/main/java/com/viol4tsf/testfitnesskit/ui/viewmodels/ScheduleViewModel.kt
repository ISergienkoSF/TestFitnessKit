package com.viol4tsf.testfitnesskit.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.viol4tsf.testfitnesskit.data.repository.Repository
import com.viol4tsf.testfitnesskit.model.ClassFitness
import kotlinx.coroutines.launch
import retrofit2.Response

class ScheduleViewModel(
    app: Application,
    private val repository: Repository
): AndroidViewModel(app) {

    val lessonsList: MutableLiveData<Response<ClassFitness>> = MutableLiveData()

    fun getAllLessons() = viewModelScope.launch {
        lessonsList.value = repository.getAllLessons()
    }
}