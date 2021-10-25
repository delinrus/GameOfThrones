package ru.skillbranch.gameofthrones.dao

import androidx.room.Dao
import ru.skillbranch.gameofthrones.data.local.entities.House

@Dao
abstract class HouseDao : BaseDao<House>("house")