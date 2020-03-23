package com.noblecilla.tasks

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.noblecilla.tasks.di.appModule
import com.noblecilla.tasks.view.setting.Mode
import com.noblecilla.tasks.view.setting.Preferences
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class KApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@KApplication)
            modules(appModule)
        }

        val nightMode = when (Preferences.nightMode(this)) {
            Mode.LIGHT.ordinal -> AppCompatDelegate.MODE_NIGHT_NO
            Mode.NIGHT.ordinal -> AppCompatDelegate.MODE_NIGHT_YES
            else -> AppCompatDelegate.MODE_NIGHT_NO
        }

        AppCompatDelegate.setDefaultNightMode(nightMode)
    }
}
