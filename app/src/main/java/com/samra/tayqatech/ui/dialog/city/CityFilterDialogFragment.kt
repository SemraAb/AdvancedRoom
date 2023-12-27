package com.samra.tayqatech.ui.dialog.city

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.samra.tayqatech.R
import com.samra.tayqatech.data.local.CityEntity
import com.samra.tayqatech.data.local.CountryEntity
import com.samra.tayqatech.databinding.DialogItemBinding
import com.samra.tayqatech.ui.dialog.country.CountryAdapter
import com.samra.tayqatech.ui.dialog.country.CountryCheckedListener
import com.samra.tayqatech.ui.person.PersonAdapter
import com.samra.tayqatech.ui.person.PersonViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CityFilterDialogFragment : DialogFragment() {
    private lateinit var binding: DialogItemBinding
    private val personViewModel: PersonViewModel by viewModels()
    private val cityAdapter: CityAdapter by lazy {
        CityAdapter(emptyList() , object: CityCheckedListener {
            override fun onCityChecked(checkedCities: List<CityEntity>) {
                selectedCities(checkedCities)
            }
        } )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= DialogItemBinding.inflate(inflater, container, false)
        binding.recyclerViewFilterItem.adapter = cityAdapter
        return (binding.root)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        personViewModel.getAllCities()
        observeData()
        binding.btnCancel.setOnClickListener{
            dialog?.dismiss()
        }
    }

    private fun observeData() {
        personViewModel.allCities.observe(viewLifecycleOwner, Observer {
            cityAdapter.onDataChange(it)
        })
    }

    private  fun selectedCities(cityList : List<CityEntity>){
        binding.btnFilter.setOnClickListener {
            personViewModel.getSelectedCities(cityList)
            dialog?.dismiss()
        }
    }
}