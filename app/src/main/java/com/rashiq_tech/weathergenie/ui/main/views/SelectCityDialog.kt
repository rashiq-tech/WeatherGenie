package com.rashiq_tech.weathergenie.ui.main.views

import android.os.Bundle
import android.view.*
import android.widget.LinearLayout
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import com.rashiq_tech.weathergenie.R
import com.rashiq_tech.weathergenie.ui.main.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult
import androidx.recyclerview.widget.*
import com.rashiq_tech.weathergenie.databinding.DialogSelectCityBinding
import com.rashiq_tech.weathergenie.ui.main.adapter.CitiesListAdapter

@AndroidEntryPoint
class SelectCityDialog : DialogFragment(R.layout.dialog_select_city),
    CitiesListAdapter.SelectCityCallback {

    private val viewModel: MainViewModel by viewModels({requireParentFragment()})
    private lateinit var binding: DialogSelectCityBinding
    private val citiesListAdapter = CitiesListAdapter(this)

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = DialogSelectCityBinding.bind(view)

        initViews()
        observeData()

    }

    private fun initViews() {
        binding.rvCities.apply {
            adapter = citiesListAdapter
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            setHasFixedSize(true)
            addItemDecoration(DividerItemDecoration(requireContext(), LinearLayout.VERTICAL))
        }
    }

    private fun observeData() {
        viewModel.cityList.observe(viewLifecycleOwner) {
            citiesListAdapter.submitList(it)
        }
        viewModel.getCityList()
    }

    override fun onCitySelect(item: String?) {
        setFragmentResult("1001", bundleOf("CITY" to item))
        dismiss()
    }

}