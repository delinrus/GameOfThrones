package ru.skillbranch.gameofthrones.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ru.skillbranch.gameofthrones.data.local.dao.CharacterDao
import ru.skillbranch.gameofthrones.data.local.dao.HouseDao
import ru.skillbranch.gameofthrones.data.local.entities.Character
import ru.skillbranch.gameofthrones.data.local.entities.House

@Database(entities = [House::class, Character::class], version = 1)
@TypeConverters(StringListConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getHouseDao(): HouseDao
    abstract fun getCharacterDao(): CharacterDao

    companion object {

        private var INSTANCE: AppDatabase? = null
        private val LOCK = Any()
        private const val DB_NAME = "game_of_thrones.db"

        fun getInstance(context: Context): AppDatabase {
            INSTANCE?.let {
                return it
            }

            synchronized(LOCK) {
                INSTANCE?.let {
                    return it
                }
            }
            val db = Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                DB_NAME
            )
                .build()
            INSTANCE = db
            return db
        }
    }
}