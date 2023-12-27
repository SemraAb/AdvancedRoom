package com.samra.tayqatech.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface PersonDao {
    @Query("SELECT * FROM countries")
    fun getCountries(): List<CountryEntity>

    @Query("SELECT * FROM cities")
    fun getCities(): List<CityEntity>

    @Query("SELECT * FROM people")
    fun getPeople(): LiveData<List<PeopleEntity>>

    @Query("SELECT * FROM countries WHERE countryId IN (:checkedCountryIds)")
    suspend fun getCountriesByCheckedIds(checkedCountryIds: List<Int>): List<CountryEntity>

    @Query("SELECT * FROM cities WHERE cityId IN (:checkedCityIds)")
    suspend fun getCitiesByCheckedIds(checkedCityIds: List<Int>): List<CityEntity>

    @Query("""
    SELECT * FROM people 
    INNER JOIN cities ON people.cityId = cities.cityId 
    INNER JOIN countries ON cities.countryId = countries.countryId
    WHERE cities.cityId IN (:selectedCityIds) AND countries.countryId IN (:selectedCountryIds)
""")
    suspend fun getPeopleByCityAndCountryIds(selectedCountryIds: List<Int> , selectedCityIds: List<Int>): List<PeopleEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCountries(countries: CountryEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCities(cities: CityEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPeople(people: PeopleEntity)
}