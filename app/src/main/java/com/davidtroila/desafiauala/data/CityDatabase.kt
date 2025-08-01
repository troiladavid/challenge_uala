package com.davidtroila.desafiauala.data

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [CityEntity::class], version = 2, autoMigrations = [ AutoMigration(from = 1, to = 2) ], exportSchema = true)
abstract class CityDatabase : RoomDatabase() {
    abstract fun cityDao(): CityDao
}