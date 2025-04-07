package so.notion.weather.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import so.notion.weather.data.api.WeatherApi
import so.notion.weather.data.model.WeatherResponse
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val api: WeatherApi
) : ViewModel()
{

    init {
        Log.d("MainViewModel", "✅ ViewModel created, testString = ")
    }


    private val _weather = MutableLiveData<WeatherResponse>()
    val weather: LiveData<WeatherResponse> get() = _weather
    private val disposable = CompositeDisposable()

    fun fetchWeather(lat: Double, lon: Double, apiKey: String) {
        val weatherCall = api.getWeatherByCoords(lat, lon, apiKey)
            .subscribeOn(Schedulers.io())                         // API call on background thread
            .observeOn(AndroidSchedulers.mainThread())            // Observe result on main thread
            .subscribe(
                { result ->
                    _weather.value = result                       // Success: post data to LiveData
                    Log.d("MainViewModel", "Weather: $result")
                },
                { error ->
                    Log.e("MainViewModel", "API Error: ${error.message}")
                }
            )

        disposable.add(weatherCall)  // Add to composite so we can clear it later
    }


    override fun onCleared() {
        disposable.clear()
        super.onCleared()
    }


}