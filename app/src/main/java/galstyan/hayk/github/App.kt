package galstyan.hayk.github

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {

    override fun onCreate() {
        super.onCreate()
    }


    companion object {
        private const val TAG = "App"
    }
}