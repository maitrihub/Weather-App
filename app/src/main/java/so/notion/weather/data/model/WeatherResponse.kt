package so.notion.weather.data.model

data class WeatherResponse(
    val name: String,
    val main: Main,
    val weather: List<Weather>,
    val wind: Wind
)

data class Main(
    val temp: Double,
    val feels_like: Double,
    val temp_min: Double,
    val temp_max: Double,
    val pressure: Int,
    val humidity: Int,
    val sea_level: Double,
    val grnd_level: Double
)

data class Weather(
    val main: String,
    val description: String,
    val icon: String,
)

data class Wind(
    val speed: Double,
    val gust: Double,
    val deg: Double
)
