package com.example.fitnesstracker

object WorkoutRepository {
    private val workouts = mutableListOf<String>()

    fun addWorkout(workout: String) {
        workouts.add(workout)
    }

    fun getWorkouts(): List<String> {
        return workouts
    }

    fun deleteWorkout(workout: String) {
        workouts.remove(workout)
    }
}
