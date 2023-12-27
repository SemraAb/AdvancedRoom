package com.samra.tayqatech.data.network.model

data class City(
    val cityId: Int,
    val name: String,
    val peopleList: List<People>
)