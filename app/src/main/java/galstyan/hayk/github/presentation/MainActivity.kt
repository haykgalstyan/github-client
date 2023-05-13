package galstyan.hayk.github.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import galstyan.hayk.github.R

@AndroidEntryPoint
class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private val vm: MainViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }


    companion object {
        private const val TAG = "MainActivity"
    }
}