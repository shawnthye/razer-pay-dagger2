package com.example.u2020

import com.example.u2020.ui.MainActivity


interface AppGraph {
    // This tells Dagger that LoginActivity requests injection so the graph needs to
    // satisfy all the dependencies of the fields that LoginActivity is requesting.
    fun inject(activity: MainActivity)
}