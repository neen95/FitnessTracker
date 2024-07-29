package com.example.fitnesstracker

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.DialogFragment

class WorkoutDialogFragment : DialogFragment() {

    private var workoutName: String? = null
    private var workoutDuration: String? = null
    private var workoutType: String? = null
    private var workoutIntensity: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        workoutName = arguments?.getString(ARG_WORKOUT_NAME)
        workoutDuration = arguments?.getString(ARG_WORKOUT_DURATION)
        workoutType = arguments?.getString(ARG_WORKOUT_TYPE)
        workoutIntensity = arguments?.getString(ARG_WORKOUT_INTENSITY)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_workout_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tvWorkoutDetails: TextView = view.findViewById(R.id.tv_workout_details)
        val btnConfirm: Button = view.findViewById(R.id.btn_confirm)

        tvWorkoutDetails.text = "Workout: $workoutName\nDuration: $workoutDuration minutes\nType: $workoutType\nIntensity: $workoutIntensity"

        btnConfirm.setOnClickListener {
            // Hide the keyboard
            val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)

            // Call saveWorkout method in WorkoutFragment
            val targetFragment = targetFragment as? WorkoutFragment
            targetFragment?.saveWorkout(workoutName ?: "", workoutDuration ?: "", workoutType ?: "", workoutIntensity ?: "")
            dismiss()
        }
    }

    companion object {
        private const val ARG_WORKOUT_NAME = "workout_name"
        private const val ARG_WORKOUT_DURATION = "workout_duration"
        private const val ARG_WORKOUT_TYPE = "workout_type"
        private const val ARG_WORKOUT_INTENSITY = "workout_intensity"

        fun newInstance(workoutName: String, workoutDuration: String, workoutType: String, intensity: String) =
            WorkoutDialogFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_WORKOUT_NAME, workoutName)
                    putString(ARG_WORKOUT_DURATION, workoutDuration)
                    putString(ARG_WORKOUT_TYPE, workoutType)
                    putString(ARG_WORKOUT_INTENSITY, intensity)
                }
            }
    }
}
