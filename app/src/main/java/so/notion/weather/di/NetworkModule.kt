package so.notion.weather.di

import android.util.Log
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import so.notion.weather.data.api.WeatherApi
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    fun provideWeatherApi(): WeatherApi {
        return try {
            val retrofit = Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build()

            val api = retrofit.create(WeatherApi::class.java)
            android.util.Log.d("NetworkModule", "✅ WeatherApi created: $api")
            api
        } catch (e: Exception) {
            android.util.Log.e("NetworkModule", "🔥 Failed to create WeatherApi", e)
            throw e
        }
    }

    @Provides
    @Singleton
    fun provideTestString(): String {
        Log.d("NetworkModule", "✅ Hilt module initialized!")
        return "hello"
    }
}