package com.example.u2020.ui.activities

import android.os.Bundle
import com.example.u2020.App
import com.example.u2020.R
import com.example.u2020.ui.AppContainer
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class DetailActivity : DaggerAppCompatActivity() {

    @Inject lateinit var app: App
    @Inject lateinit var appContainer: AppContainer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val container = appContainer.getContainer(this)
        layoutInflater.inflate(R.layout.activity_detail, container)
    }
}