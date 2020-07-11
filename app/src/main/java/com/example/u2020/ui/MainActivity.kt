package com.example.u2020.ui

import android.app.Application
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.u2020.App
import com.example.u2020.R
import com.example.u2020.component
import javax.inject.Inject

class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var app: Application

    override fun onCreate(savedInstanceState: Bundle?) {
        application.component.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }
}