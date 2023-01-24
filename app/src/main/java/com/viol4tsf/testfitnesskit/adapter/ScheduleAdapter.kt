package com.viol4tsf.testfitnesskit.adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.viol4tsf.testfitnesskit.databinding.LessonItemViewBinding
import com.viol4tsf.testfitnesskit.model.ClassFitness
import com.viol4tsf.testfitnesskit.model.Lesson
import com.viol4tsf.testfitnesskit.model.Trainer
import retrofit2.Response
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.Duration
import java.time.LocalTime
import java.util.*

class ScheduleAdapter: RecyclerView.Adapter<ScheduleAdapter.ScheduleViewHolder>() {

    var listTrainers = emptyList<Trainer>()
    var listLessons = emptyList<Lesson>()

    class ScheduleViewHolder(val itemBinding: LessonItemViewBinding): RecyclerView.ViewHolder(itemBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScheduleViewHolder {
        return ScheduleViewHolder(
            LessonItemViewBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    @SuppressLint("SimpleDateFormat")
    override fun onBindViewHolder(holder: ScheduleViewHolder, position: Int) {
        val currentLesson = listLessons[position]
        var coach = ""
        (1..listTrainers.size).forEach{
            if (currentLesson.coach_id == listTrainers[it-1].id){
                coach = listTrainers[it].full_name
            }
        }

        val startTime = LocalTime.parse(currentLesson.startTime)
        val endTime = LocalTime.parse(currentLesson.endTime)
        val duration = Duration.between(startTime, endTime)
        val asHours = duration.toHours()
        val asMinutes = duration.toMinutes() - asHours * 60
        var finalDuration: String
        if (asHours == 0L){
            finalDuration = "${asMinutes}мин."
        } else {
            finalDuration = "${asHours}ч. ${asMinutes}мин."
        }

        holder.itemBinding.classNameTextView.text = currentLesson.name
        holder.itemBinding.startTimeTextView.text = currentLesson.startTime
        holder.itemBinding.endTimeTextView.text = currentLesson.endTime
        holder.itemBinding.trainerTextView.text = coach
        holder.itemBinding.durationTextView.text = finalDuration
        holder.itemBinding.locationTextView.text = currentLesson.place
        holder.itemBinding.colorView.setBackgroundColor(Color.parseColor(currentLesson.color))
    }

    override fun getItemCount(): Int {
        return listLessons.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setList(listL: List<Lesson>, listC: List<Trainer>){
        listLessons = listL
        listTrainers = listC
        notifyDataSetChanged()
    }
}