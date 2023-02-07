package com.rashiq_tech.weathergenie.ui.main.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.rashiq_tech.weathergenie.R
import com.rashiq_tech.weathergenie.databinding.FragmentMainBinding
import com.rashiq_tech.weathergenie.ui.main.viewmodel.MainViewModel
import com.rashiq_tech.weathergenie.utils.ApiState
import com.rashiq_tech.weathergenie.utils.Common
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment(R.layout.fragment_main) {

    private val viewModel: MainViewModel by viewModels()
    private lateinit var binding: FragmentMainBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentMainBinding.bind(view)

        observeData()

    }

    private fun observeData() {
        viewModel.getWeatherAgainstCity("London")
        viewModel.weatherInfoState.observe(requireActivity()) {
            when (it) {
                is ApiState.Success -> {
                    val data = it.data!!
                    binding.tvCityName.text = "London"
                    binding.tvTemp.text = String.format(getString(R.string.placeholder_degree), data.mainObject.temp.toString())
                    binding.tvMinTemp.text = String.format(getString(R.string.placeholder_degree), data.mainObject.tempMin.toString())
                    binding.tvMaxTemp.text = String.format(getString(R.string.placeholder_degree), data.mainObject.tempMax.toString())
                    binding.tvFeelsLikeTemp.text = String.format(getString(R.string.placeholder_degree), data.mainObject.feelsLike.toString())
                }
                is ApiState.Error -> {
                    it.message?.let { message ->
                       Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
                    }
                }
                is ApiState.Loading -> {
                    Toast.makeText(requireContext(), "Loading.....", Toast.LENGTH_SHORT).show()
                }
            }

        }
    }

}