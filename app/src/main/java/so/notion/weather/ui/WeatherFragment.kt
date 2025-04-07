package so.notion.weather.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import so.notion.weather.databinding.FragmentWeatherBinding

@AndroidEntryPoint
class WeatherFragment : Fragment() {
    private var _binding: FragmentWeatherBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by viewModels()
    private val lat = 40.7128
    private val lon = -74.0060
    private val apiKey = "dda3a0a36330226ebe8e745cd1971571"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.d("MainViewModel", "onCreateView called ✅")
        _binding = FragmentWeatherBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        try {
            viewModel.fetchWeather(lat, lon, apiKey)

            viewModel.weather.observe(viewLifecycleOwner) {
                binding.city.text = it.name
                val text = (String.format("%.2f",((it.main.temp - 273.15)*1.8 + 32)))
                val feels_like =  (String.format("%.2f",((it.main.feels_like - 273.15)*1.8 + 32)))
                binding.temp.text = "$text°F"
                binding.description.text = "${it.weather.firstOrNull()?.description}"
                binding.feelsLike.text = "Feels Like: ${feels_like}°F"
                binding.wind.text = "Wind: ${it.wind.speed} km/s"
            }

        } catch (e: Exception) {
            Log.e("DEBUGGER YAY", "🔥 ViewModel crash: ${e.message}", e)
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

}
