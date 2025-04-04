package so.notion.weather.data.api

import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query
import so.notion.weather.data.model.WeatherResponse

interface WeatherApi {
    @GET("data/2.5/weather")
    fun getWeatherByCoords(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("exclude") exclude: String = "minutely,hourly,alerts",
        @Query("appid") apiKey: String
    ): Single<WeatherResponse>
}
