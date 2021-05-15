package com.divyesh.exercise

import android.app.Application
import com.divyesh.exercise.di.AppModule
import org.koin.core.context.startKoin
import org.koin.dsl.module

class ExerciseApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin { modules(AppModule().appModule) }
    }
}
