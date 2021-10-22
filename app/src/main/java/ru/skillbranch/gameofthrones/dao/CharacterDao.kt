package ru.skillbranch.gameofthrones.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import ru.skillbranch.gameofthrones.data.local.entities.Character

@Dao
interface CharacterDao {
    @Insert
    fun insertAll(vararg characters: Character)

    @Query("SELECT * FROM character")
    fun getAll(): List<Character>

    @Query("DELETE FROM character")
    fun deleteAll()
}