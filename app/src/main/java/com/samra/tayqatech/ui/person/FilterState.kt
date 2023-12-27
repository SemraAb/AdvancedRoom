package com.samra.tayqatech.ui.person

import com.samra.tayqatech.data.local.CityEntity
import com.samra.tayqatech.data.local.CountryEntity

data class FilterState(
    val selectedCounties: List<CountryEntity> = emptyList(),
    val selectedCities: List<CityEntity> = emptyList(),
)