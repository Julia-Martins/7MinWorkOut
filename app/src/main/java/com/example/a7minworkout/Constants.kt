package com.example.a7minworkout

object Constants {

    fun defaultExerciseList(): ArrayList<ExerciseModel>{
        val exerciseList = ArrayList<ExerciseModel>()
        val crunches = ExerciseModel(
            1,
            "Crunches",
            R.drawable.exercise_1,
            false,
            false
        )

        exerciseList.add(crunches)

        val lunges = ExerciseModel(
            2,
            "Lunges",
            R.drawable.exercise_2,
            false,
            false
        )

        exerciseList.add(lunges)

        val plank = ExerciseModel(
            3,
            "Plank",
            R.drawable.exercise_3,
            false,
            false
        )

        exerciseList.add(plank)

        val push_up = ExerciseModel(
            4,
            "Push-Up",
            R.drawable.exercise_4,
            false,
            false
        )

        exerciseList.add(push_up)

        val spine_stretch = ExerciseModel(
            5,
            "Spine Stretch",
            R.drawable.exercise_5,
            false,
            false
        )

        exerciseList.add(spine_stretch)

        val high_knees = ExerciseModel(
            6,
            "High Knees",
            R.drawable.exercise_6,
            false,
            false
        )

        exerciseList.add(high_knees)

        val bridges = ExerciseModel(
            7,
            "Bridges",
            R.drawable.exercise_7,
            false,
            false
        )

        exerciseList.add(bridges)

        val backstretch = ExerciseModel(
            8,
            "Backstretch",
            R.drawable.exercise_8,
            false,
            false
        )

        exerciseList.add(backstretch)

        val sideLegRaise = ExerciseModel(
            9,
            "Side Leg Raise",
            R.drawable.exercise_9,
            false,
            false
        )

        exerciseList.add(sideLegRaise)

        val lateralFlexionStretch = ExerciseModel(
            10,
            "Lateral Flexion Stretch",
            R.drawable.exercise_10,
            false,
            false
        )

        exerciseList.add(lateralFlexionStretch)

        return exerciseList
    }

}