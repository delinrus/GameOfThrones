package ru.skillbranch.gameofthrones.data.local.dao

import androidx.room.Dao
import ru.skillbranch.gameofthrones.data.local.entities.House

@Dao
abstract class HouseDao : BaseDao<House>("house")