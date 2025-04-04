package so.notion.interview

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import org.w3c.dom.Text
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import so.notion.interview.data.api.WeatherApi
import so.notion.interview.data.model.WeatherResponse

class MainActivity : ComponentActivity() {
    private lateinit var api: WeatherApi
    private val disposable = CompositeDisposable()
    private val lat = 40.7128
    private val lon = -74.0060
    private val apiKey = "dda3a0a36330226ebe8e745cd1971571"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Set the content view to the activity_main XML layout
        setContentView(R.layout.activity_main)

        api = Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()
            .create(WeatherApi::class.java)

        fetchWeather()
    }

    private fun fetchWeather() {
        val temperature = findViewById<TextView>(R.id.temp)
        // val wind = findViewById<TextView>(R.id.wind)
        disposable.add(
            api.getWeatherByCoords(lat, lon, apiKey = apiKey)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    response ->
                    val temp = (response.main.temp -273.15)*1.8 + 32
                    val string_temp = String.format("%.2f", temp)
                    temperature.text = string_temp + "°F"
//                    wind.text = response.wind.speed.toString() + "mph"
                    Log.d("fetchWeather", response.weather.toString())
                },{ error -> Log.e("fetchWeather", "$error") })
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable.clear()
    }
}
