package com.williamdsk.todolist.ui

import android.os.Bundle
import android.os.PersistableBundle
import android.util.*
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import com.williamdsk.todolist.databinding.ActivityAddTaskBinding
import com.williamdsk.todolist.datasource.TaskDataSource
import com.williamdsk.todolist.extensions.format
import com.williamdsk.todolist.extensions.text
import com.williamdsk.todolist.model.Task
import java.util.*

class AddTaskActivity : AppCompatActivity(){

    private lateinit var binding: ActivityAddTaskBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)

        insertListeners()
    }

    private fun insertListeners(){
        binding.tilDate.editText?.setOnClickListener {
            val datePìcker = MaterialDatePicker.Builder.datePicker().build()

            datePìcker.addOnPositiveButtonClickListener { it ->
                val timeZone = TimeZone.getDefault()
                val offset = timeZone.getOffset(Date().time) * -1
                binding.tilDate.text = Date(it + offset).format()
            }

            datePìcker.show(supportFragmentManager, "DATE_PICKER_TAG")
        }

        binding.tilHour.editText?.setOnClickListener {
            val timePicker = MaterialTimePicker.Builder()
                                .setTimeFormat(TimeFormat.CLOCK_24H)
                                .build()

            timePicker.addOnPositiveButtonClickListener{
                val hour = timePicker.hour.toString().padStart(2, '0')
                val minute = timePicker.minute.toString().padStart(2, '0')
                binding.tilHour.text = "$hour:$minute"
            }

            timePicker.show(supportFragmentManager, null)
        }

        binding.btnCancel.setOnClickListener {
            finish()
        }

        binding.btnNewTask.setOnClickListener {
            val task = Task(
                title = binding.tilTitle.text,
                description = binding.tilDesc.text,
                date = binding.tilDate.text,
                hour = binding.tilHour.text
            )
            TaskDataSource.insertTask(task)
            Log.e("TAG", "" + TaskDataSource.getList())
        }
    }

}