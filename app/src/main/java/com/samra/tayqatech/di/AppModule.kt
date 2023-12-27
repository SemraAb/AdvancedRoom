package com.samra.tayqatech.di

import android.content.Context
import androidx.room.Room
import com.samra.tayqatech.Constants
import com.samra.tayqatech.data.local.PersonDao
import com.samra.tayqatech.data.local.PersonDatabase
import com.samra.tayqatech.data.network.service.PeopleApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {


    @Provides
    @Singleton
    fun provideNewsDao(db: PersonDatabase): PersonDao = db.personDao()

    @Singleton
    @Provides
    fun provideRoomDatabase(@ApplicationContext context: Context): PersonDatabase =
        Room.databaseBuilder(
            context,
            PersonDatabase::class.java,
            "SavedPeopleDB"
        ).fallbackToDestructiveMigration().build()


    @Singleton
    @Provides
    fun provideRetrofit() =
        Retrofit.Builder().baseUrl(Constants.baseUrl)
            .addConverterFactory(
                GsonConverterFactory.create()
            ).build()

    @Singleton
    @Provides
    fun provideApi(retrofit: Retrofit) = retrofit.create(PeopleApi::class.java)

}