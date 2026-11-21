package com.example.test_task

import android.app.Application
import com.example.test_task.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MyApp: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MyApp);
            androidLogger();
            modules(appModule)
        }
    }
}