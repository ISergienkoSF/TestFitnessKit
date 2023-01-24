package com.viol4tsf.testfitnesskit.data.api

import com.viol4tsf.testfitnesskit.model.ClassFitness
import com.viol4tsf.testfitnesskit.model.Lesson
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("schedule/get_v3/?club_id=2")
    suspend fun getAllLessons(): Response<ClassFitness>
}