package com.example.fitnesstracker

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import java.util.*

class WorkoutFragment : Fragment() {

    private var selectedDate: String? = null
    private var selectedTime: String? = null
    private lateinit var spinnerWorkoutType: Spinner
    private lateinit var radioGroupIntensity: RadioGroup

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_workout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val etWorkoutName: EditText = view.findViewById(R.id.et_workout_name)
        val etWorkoutDuration: EditText = view.findViewById(R.id.et_workout_duration)
        val btnLogWorkout: Button = view.findViewById(R.id.btn_log_workout)

        spinnerWorkoutType = view.findViewById(R.id.spinner_workout_type)
        radioGroupIntensity = view.findViewById(R.id.radioGroupIntensity)

        // Initialize spinner with workout types
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.workout_types,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerWorkoutType.adapter = adapter
        }

        btnLogWorkout.setOnClickListener {
            val workoutName = etWorkoutName.text.toString()
            val workoutDuration = etWorkoutDuration.text.toString()
            val workoutType = spinnerWorkoutType.selectedItem.toString()
            val intensity = when (radioGroupIntensity.checkedRadioButtonId) {
                R.id.radio_low -> "Low"
                R.id.radio_medium -> "Medium"
                R.id.radio_high -> "High"
                else -> ""
            }

            if (workoutName.isNotEmpty() && workoutDuration.isNotEmpty() && intensity.isNotEmpty()) {
                val dialog = WorkoutDialogFragment.newInstance(workoutName, workoutDuration, workoutType, intensity)
                dialog.setTargetFragment(this, 1)
                dialog.show(parentFragmentManager, "WorkoutDialogFragment")
            } else {
                Toast.makeText(context, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun saveWorkout(workoutName: String, workoutDuration: String, workoutType: String, intensity: String) {
        val workout = "Workout: $workoutName\nDuration: $workoutDuration minutes\nType: $workoutType\nIntensity: $intensity"
        WorkoutRepository.addWorkout(workout)

        // Show success message
        Toast.makeText(context, "Workout saved successfully", Toast.LENGTH_SHORT).show()

        // Clear input fields
        view?.findViewById<EditText>(R.id.et_workout_name)?.text?.clear()
        view?.findViewById<EditText>(R.id.et_workout_duration)?.text?.clear()
        spinnerWorkoutType.setSelection(0)
        radioGroupIntensity.clearCheck()
    }
}
