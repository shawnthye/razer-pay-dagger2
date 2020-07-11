package com.example.u2020

import android.app.Application

val Application.component: AppComponent
    get() = (this as App).component()