package com.samra.tayqatech.repository

import android.provider.Contacts.People
import android.util.Log
import com.samra.tayqatech.data.local.CityEntity
import com.samra.tayqatech.data.local.CountryEntity
import com.samra.tayqatech.data.local.PeopleEntity
import com.samra.tayqatech.data.local.PersonDao
import com.samra.tayqatech.data.network.model.Result
import com.samra.tayqatech.data.network.service.PeopleApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PersonRepository @Inject constructor(
    private val personDao: PersonDao,
    private val api: PeopleApi
) {

    fun getAllPeople() = personDao.getPeople()

    suspend fun getAllCountries(): Flow<List<CountryEntity>> = flow {
        val result = personDao.getCountries()
        if (result != null) {
            emit(result)
        }
    }

    suspend fun getAllCities(): Flow<List<CityEntity>> = flow {
        val result = personDao.getCities()
        if (result != null) {
            emit(result)
        }
    }

    suspend fun getAllFromApi(): Flow<Result> = flow {
        val result = api.getAll().body()
        if (result != null) {
            emit(result)
        }
    }

    suspend fun getPeopleByCityAndCountryIds(
        checkedCountryIds: List<Int>,
        checkedCityIds: List<Int>
    ):List<PeopleEntity>{
        var peopleData =  personDao.getPeopleByCityAndCountryIds(checkedCountryIds, checkedCityIds)
        if(peopleData.isNotEmpty()){
            return peopleData
        }
        return peopleData
//        Log.e("people filtered", "observeData: ${peopleData}", )
    }

    suspend fun getCountriesByCheckedIds(checkedCountryIds: List<Int>): Flow<List<CountryEntity>> =
        flow {
            var result = personDao.getCountriesByCheckedIds(checkedCountryIds)
            emit(result)

        }

    suspend fun getCitiesByCheckedIds(checkedCityIds: List<Int>): Flow<List<CityEntity>> =
        flow {
            var result = personDao.getCitiesByCheckedIds(checkedCityIds)
            emit(result)
        }


    fun insertCountry(country: CountryEntity) {
        personDao.insertCountries(country)
    }

    fun insertCity(city: CityEntity) {
        personDao.insertCities(city)
    }

    fun insertPeople(people: PeopleEntity) {
        personDao.insertPeople(people)
    }
}

