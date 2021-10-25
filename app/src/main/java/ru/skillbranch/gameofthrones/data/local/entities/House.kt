package ru.skillbranch.gameofthrones.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.skillbranch.gameofthrones.data.remote.res.HouseRes
import ru.skillbranch.gameofthrones.extensions.getIdFromUrl
import java.util.regex.Pattern

@Entity
data class House(
    @PrimaryKey
    val id: String,
    val name: String,
    val region: String,
    val coatOfArms: String,
    val words: String,
    val titles: String,
    val seats: String,
    val currentLord: String, //rel
    val heir: String, //rel
    val overlord: String,
    val founded: String,
    val founder: String, //rel
    val diedOut: String,
    val ancestralWeapons: String
)