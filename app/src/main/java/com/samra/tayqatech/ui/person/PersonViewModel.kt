package com.samra.tayqatech.ui.person

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.samra.tayqatech.data.local.CityEntity
import com.samra.tayqatech.data.local.CountryEntity
import com.samra.tayqatech.data.local.PeopleEntity
import com.samra.tayqatech.data.network.model.City
import com.samra.tayqatech.data.network.model.Country
import com.samra.tayqatech.data.network.model.People
import com.samra.tayqatech.data.network.model.Result
import com.samra.tayqatech.data.network.service.PeopleApi
import com.samra.tayqatech.repository.PersonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class PersonViewModel @Inject constructor(private val personRepository: PersonRepository) :
    ViewModel() {
    val people = MutableLiveData<List<PeopleEntity>?>()
    val allCountries = MutableLiveData<List<CountryEntity>>()
    val allCities = MutableLiveData<List<CityEntity>>()

    // filter
    private val _filterState = MutableStateFlow(FilterState(emptyList(), emptyList()))
    val filterState: StateFlow<FilterState> = _filterState


    // get country and cities
    fun getAllCountries() {
        CoroutineScope(Dispatchers.IO).launch {
            personRepository.getAllCountries().collect() {
                allCountries.postValue(it)
            }
        }
    }

    fun getAllCities() {
        CoroutineScope(Dispatchers.IO).launch {
            personRepository.getAllCities().collect() {
                allCities.postValue(it)
            }
        }
    }
    // get selected countries and cities

    fun getSelectedCountries(country: List<CountryEntity>) {
        CoroutineScope(Dispatchers.IO).launch {
            var selectedId = mutableListOf<Int>()
            country.forEach {
                selectedId.add(it.countryId)
            }
            val countries = personRepository.getCountriesByCheckedIds(selectedId)

            // Update the filterState after collecting the countries
            countries.collect { fetchedCountries ->
                val updatedFilterState =
                    _filterState.value.copy(selectedCounties = fetchedCountries)
                _filterState.value = updatedFilterState
            }

        }
    }

    fun getSelectedCities(city: List<CityEntity> = filterState.value.selectedCities) {
        CoroutineScope(Dispatchers.IO).launch {
            var selectedId = mutableListOf<Int>()
            city.forEach {
                selectedId.add(it.cityId)
            }
            val cities = personRepository.getCitiesByCheckedIds(selectedId)

            // Update the filterState after collecting the countries
            cities.collect { fetchedCities ->
                val updatedFilterState =
                    _filterState.value.copy(selectedCities = fetchedCities)
                _filterState.value = updatedFilterState

            }

        }
    }


    // get people
    fun fetchPeopleData() = personRepository.getAllPeople()

    fun fetchPeopleFromApi() {
        CoroutineScope(Dispatchers.IO).launch {
            personRepository.getAllFromApi().collect { result ->
                if (result != null) {
                    result.countryList.forEach { country ->
                        // Insert CountryEntity
                        personRepository.insertCountry(mapToCountryEntity(country))
                        var countryId = country.countryId
                        country.cityList.forEach { city ->
                            // Insert CityEntity
                            personRepository.insertCity(
                                mapToCityEntity(
                                    city,
                                    countryId
                                )
                            )
                            var cityId = city.cityId
                            city.peopleList.forEach { person ->
                                // Insert PeopleEntity
                                val peopleEntity =
                                    mapToPeopleEntity(person, cityId)
                                personRepository.insertPeople(peopleEntity)
                            }
                        }
                    }
                }
            }
        }
    }

    // get filtered people
    suspend fun getFilteredPeople(
        selectedCountries: List<Int>,
        selectedCities: List<Int>
    ): List<PeopleEntity> {
        return withContext(Dispatchers.IO) {
            val filteredResult = personRepository.getPeopleByCityAndCountryIds(selectedCountries, selectedCities)
            filteredResult
        }
    }

    fun mapToCountryEntity(country: Country): CountryEntity {
        return CountryEntity(
            countryId = country.countryId,
            name = country.name
        )
    }

    fun mapToCityEntity(city: City, countryId: Int): CityEntity {
        return CityEntity(
            cityId = city.cityId,
            countryId = countryId,
            name = city.name
        )
    }

    fun mapToPeopleEntity(people: People, cityId: Int): PeopleEntity {
        return PeopleEntity(
            humanId = people.humanId,
            cityId = cityId,
            name = people.name,
            surname = people.surname
        )
    }
}