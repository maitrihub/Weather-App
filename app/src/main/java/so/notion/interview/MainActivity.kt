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
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import so.notion.interview.data.api.WeatherApi
import so.notion.interview.data.model.WeatherResponse

class MainActivity : ComponentActivity() {
    private lateinit var tvWeather: TextView
    private lateinit var api: WeatherApi
    private val disposable = CompositeDisposable()
    private val lat = 40.7128
    private val lon = -74.0060
    private val apiKey = "5b024685124dae3281ca1c8477f05a8f"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Set the content view to the activity_main XML layout
        setContentView(R.layout.activity_main)
        tvWeather = findViewById(R.id.tvWeather)

        api = Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()
            .create(WeatherApi::class.java)

        fetchWeather()
    }

    private fun fetchWeather() {
        disposable.add(
            api.getWeatherByCoords(lat, lon, api_key = apiKey)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    response -> showWeather(response)
                    Log.d("fetchWeather", response.toString())
                },{ error -> Log.e("fetchWeather", "$error") })
        )
    }

    private fun showWeather(response: WeatherResponse) {
        val temp = response.main.temp
        val wind = response.wind.speed
        val city = response.name

        val result = """"Temp: $temp" +
                Wind: "$wind" +
                City: "$city"""

        tvWeather.text = result
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable.clear()
    }
}
