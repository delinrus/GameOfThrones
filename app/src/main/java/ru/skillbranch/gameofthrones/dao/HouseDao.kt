package ru.skillbranch.gameofthrones.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import ru.skillbranch.gameofthrones.data.local.entities.Character
import ru.skillbranch.gameofthrones.data.local.entities.House

@Dao
interface HouseDao {

    @Insert
    fun insertAll(houses: List<House>)

    @Query("SELECT * FROM house")
    fun getAll(): List<House>

    @Query("SELECT * FROM house WHERE id = :id")
    fun getById(id: String): House

    @Query("DELETE FROM house")
    fun deleteAll()

    @Query("SELECT COUNT(id) FROM house")
    fun count(): Int
}