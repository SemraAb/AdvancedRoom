package com.samra.tayqatech.ui.person

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.samra.tayqatech.data.local.PeopleEntity
import com.samra.tayqatech.databinding.FragmentPersonBinding
import com.samra.tayqatech.ui.dialog.city.CityFilterDialogFragment
import com.samra.tayqatech.ui.dialog.country.CountryFilterDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.observeOn
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PersonFragment : Fragment() {
    private lateinit var binding: FragmentPersonBinding
    private val personViewModel: PersonViewModel by viewModels()
    private val personAdapter: PersonAdapter by lazy {
        PersonAdapter(emptyList())
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPersonBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerView.adapter = personAdapter
        defineDialogFragments()
        observeData()
//        personViewModel.getSelectedCountries()

    }

    private fun defineDialogFragments() {
        binding.countryIcon.setOnClickListener {
            val countryFilterDialog = CountryFilterDialogFragment()
            countryFilterDialog.show(parentFragmentManager, "CountryFilterDialog")
        }
        binding.cityIcon.setOnClickListener {
            val cityFilterDialog = CityFilterDialogFragment()
            cityFilterDialog.show(parentFragmentManager, "CityFilterDialog")
        }
    }

    private fun observeData() {
        personViewModel.fetchPeopleData().observe(viewLifecycleOwner) {
            if (it.isNullOrEmpty()) {
                personViewModel.fetchPeopleFromApi()
                personAdapter.onDataChange(it)
            } else {
                personAdapter.onDataChange(it)
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            personViewModel.filterState.collect { filterState ->
                val selectedCountries = filterState.selectedCounties
                val selectedCity = filterState.selectedCities
                var selectedCountryId = mutableListOf<Int>()
                var selectedCityId = mutableListOf<Int>()
                Log.d("selected countries", "Selected : ${selectedCountries}")

                if (selectedCountries.isNotEmpty() || selectedCity.isNotEmpty()){
                    selectedCountries.forEach {
                        selectedCountryId.add(it.countryId)
                    }
                    selectedCity.forEach {
                        selectedCityId.add(it.cityId)
                    }
                    var filteredPeople =  personViewModel.getFilteredPeople(selectedCountryId , selectedCityId)
                    personAdapter.onDataChange(filteredPeople)
                }
            }
        }
    }
}