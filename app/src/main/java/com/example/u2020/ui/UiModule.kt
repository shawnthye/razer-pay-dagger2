package com.example.u2020.ui

import com.example.u2020.ui.activities.DetailActivity
import com.example.u2020.ui.activities.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class UiModule {

    @ContributesAndroidInjector
    abstract fun mainActivity(): MainActivity

    @ContributesAndroidInjector
    abstract fun detailActivity(): DetailActivity
}