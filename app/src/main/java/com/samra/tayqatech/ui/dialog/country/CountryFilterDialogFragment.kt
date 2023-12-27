package com.samra.tayqatech.ui.dialog.country

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.samra.tayqatech.data.local.CountryEntity
import com.samra.tayqatech.databinding.DialogItemBinding
import com.samra.tayqatech.ui.person.PersonViewModel
import dagger.hilt.android.AndroidEntryPoint
import hilt_aggregated_deps._dagger_hilt_android_flags_FragmentGetContextFix_FragmentGetContextFixEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CountryFilterDialogFragment : DialogFragment() {
    private var _binding: DialogItemBinding? = null
    private val binding get() = _binding!!
    private val personViewModel: PersonViewModel by viewModels()
    private val countryAdapter: CountryAdapter by lazy {
        CountryAdapter(emptyList() , object: CountryCheckedListener{
            override fun onCountryChecked(checkedCountries: List<CountryEntity>) {
                selectedCountries(checkedCountries)
            }
        } )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DialogItemBinding.inflate(inflater, container, false)
        binding.recyclerViewFilterItem.adapter = countryAdapter
        return (binding.root)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeData()
        personViewModel.getAllCountries()
        binding.btnCancel.setOnClickListener {
            dialog?.dismiss()
        }
    }

    private fun observeData() {
        personViewModel.allCountries.observe(viewLifecycleOwner, Observer {
            countryAdapter.onDataChange(it)
        })
    }

    private  fun selectedCountries(countryList : List<CountryEntity>){
        binding.btnFilter.setOnClickListener {
            personViewModel.getSelectedCountries(countryList)
            dialog?.dismiss()
        }
    }
}