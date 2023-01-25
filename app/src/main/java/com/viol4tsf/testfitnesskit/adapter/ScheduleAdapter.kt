package com.viol4tsf.testfitnesskit.adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.viol4tsf.testfitnesskit.databinding.LessonItemViewBinding
import com.viol4tsf.testfitnesskit.model.DateLesson
import com.viol4tsf.testfitnesskit.model.Lesson
import com.viol4tsf.testfitnesskit.model.Trainer
import java.time.*
import java.time.format.DateTimeFormatter
import java.util.*

class ScheduleAdapter: RecyclerView.Adapter<ScheduleAdapter.ScheduleViewHolder>() {

    var listTrainers = emptyList<Trainer>()
    var listLessons = emptyList<Lesson>()
    private var dateNeeded = mutableListOf(true)
    private var listDatesWithLesson = mutableListOf<DateLesson>()

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
        val currentLesson = listDatesWithLesson[position].lesson
        var coach = ""
        (1..listTrainers.size).forEach{
            if (currentLesson.coach_id == listTrainers[it-1].id){
                coach = listTrainers[it-1].full_name
            }
        }

        val startTime = LocalTime.parse(currentLesson.startTime)
        val endTime = LocalTime.parse(currentLesson.endTime)
        val duration = Duration.between(startTime, endTime)
        val asHours = duration.toHours()
        val asMinutes = duration.toMinutes() - asHours * 60
        val finalDuration: String
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
        if (dateNeeded[position]){
            holder.itemBinding.dateTextView.visibility = View.VISIBLE
            holder.itemBinding.dateTextView.text = listDatesWithLesson[position].date
        } else {
            holder.itemBinding.dateTextView.visibility = View.GONE
        }
    }

    override fun getItemCount(): Int {
        return listLessons.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setList(listL: List<Lesson>, listC: List<Trainer>){
        listL.sortedBy { it.date }
        listLessons = listL
        listTrainers = listC
        createDateWithLessonList(listLessons)
        createDateIfNeededList(listDatesWithLesson)
        notifyDataSetChanged()
    }

    private fun createDateIfNeededList(listD: List<DateLesson>){
        (1 until listD.size).forEach{
            if (listD[it].lesson.date != listD[it-1].lesson.date){
                dateNeeded.add(true)
            } else {
                dateNeeded.add(false)
            }
        }
    }

    private fun createDateWithLessonList(listL: List<Lesson>){
        (1 .. listL.size).forEach{
            val str = "${listL[it-1].date} ${listL[it-1].startTime}"
            val dateTime = LocalDateTime.parse(str, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))
            val date = LocalDate.parse(listL[it-1].date, DateTimeFormatter.ofPattern("yyyy-MM-dd"))
            val dateTimeLong: Long =
                dateTime.toEpochSecond(ZoneOffset.UTC)

            listDatesWithLesson.add(DateLesson(dateTimeLong, date.toString(), listL[it-1]))
        }
        listDatesWithLesson.sortBy { it.dateTime }
    }
}