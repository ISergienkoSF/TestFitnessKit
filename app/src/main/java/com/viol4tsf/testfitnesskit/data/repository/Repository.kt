package com.viol4tsf.testfitnesskit.data.repository

import com.viol4tsf.testfitnesskit.data.api.RetrofitInstance
import com.viol4tsf.testfitnesskit.model.ClassFitness
import retrofit2.Response

class Repository {
    suspend fun getAllLessons(): Response<ClassFitness>{
        return RetrofitInstance.api.getAllLessons()
    }
}