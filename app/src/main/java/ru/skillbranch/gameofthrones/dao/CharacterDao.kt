package ru.skillbranch.gameofthrones.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import ru.skillbranch.gameofthrones.data.local.entities.Character

@Dao
interface CharacterDao {

    @Insert
    fun insertAll(characters: List<Character>)

    @Query("SELECT * FROM character")
    fun getAll(): List<Character>

    @Query("SELECT * FROM character WHERE houseId = :houseId")
    fun getByHouse(houseId: String): List<Character>

    @Query("SELECT * FROM character WHERE id = :id")
    fun getById(id: String): Character

    @Query("DELETE FROM character")
    fun deleteAll()

    @Query("SELECT COUNT(id) FROM house")
    fun count(): Int
}