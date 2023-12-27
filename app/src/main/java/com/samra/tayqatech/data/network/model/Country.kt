package com.samra.tayqatech.data.network.model

data class Country(
    val cityList: List<City>,
    val countryId: Int,
    val name: String
)