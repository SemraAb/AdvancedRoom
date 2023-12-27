package com.samra.tayqatech.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "countries")
data class CountryEntity(@PrimaryKey val countryId: Int , val name: String)

@Entity(tableName = "cities")
data class CityEntity(
    @PrimaryKey val cityId: Int ,
    val countryId: Int, //Foreign key referencing CountryEntity
    val name: String
)

@Entity(tableName = "people")
data class PeopleEntity(
    @PrimaryKey val humanId: Int ,
    val cityId: Int, // Foreign key referencing CityEntries
    val name: String, val surname: String
)