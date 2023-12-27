package com.samra.tayqatech.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [CountryEntity::class, CityEntity::class, PeopleEntity::class], version = 1)
abstract class PersonDatabase : RoomDatabase() {
    abstract fun personDao(): PersonDao
}