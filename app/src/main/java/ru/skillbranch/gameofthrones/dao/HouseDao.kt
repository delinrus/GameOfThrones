package ru.skillbranch.gameofthrones.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import ru.skillbranch.gameofthrones.data.local.entities.House

@Dao
interface HouseDao {

    @Insert
    fun insertAll(vararg houses: House)

    @Query("SELECT * FROM house")
    fun getAllHouses() : List<House>

}