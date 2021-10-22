package uz.ismoil.notes

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import java.io.InputStream
import kotlin.math.max
import kotlin.random.Random

@HiltAndroidApp
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

}
