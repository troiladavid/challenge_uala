package com.davidtroila.desafiauala.di

import android.content.Context
import androidx.room.Room
import com.davidtroila.desafiauala.data.CityDao
import com.davidtroila.desafiauala.data.CityDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): CityDatabase =
        Room.databaseBuilder(context, CityDatabase::class.java, "city-db").build()

    @Provides
    fun provideCityDao(db: CityDatabase): CityDao = db.cityDao()
}