package ru.skillbranch.gameofthrones.db

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.skillbranch.gameofthrones.dao.HouseDao
import ru.skillbranch.gameofthrones.data.local.entities.House

@Database(entities = [House::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getHouseDao(): HouseDao
}