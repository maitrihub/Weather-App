package so.notion.weather.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.disposables.CompositeDisposable
import so.notion.weather.R

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val disposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Set the content view to the activity_main XML layout
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, WeatherFragment()) // 👈 This adds it
                .commit()
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        disposable.clear()
    }
}
