package ru.skillbranch.gameofthrones.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import ru.skillbranch.gameofthrones.data.local.entities.Character

@Dao
abstract class CharacterDao: BaseDao<Character>("character") {

    @Query("SELECT * FROM character WHERE houseId = :houseId")
    abstract fun getByHouse(houseId: String): List<Character>
}