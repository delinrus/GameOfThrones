package ru.skillbranch.gameofthrones.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.skillbranch.gameofthrones.data.remote.res.HouseRes

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
) {
    constructor(houseRes: HouseRes) : this(
        houseRes.url.getIdFromUrl(), // houseRes.id,
        houseRes.name,
        houseRes.region,
        houseRes.coatOfArms,
        houseRes.words,
        houseRes.titles[0],
        houseRes.seats[0],
        houseRes.currentLord.getIdFromUrl(),
        houseRes.heir.getIdFromUrl(),
        houseRes.overlord,
        houseRes.founded,
        houseRes.founder.getIdFromUrl(),
        houseRes.diedOut,
        houseRes.ancestralWeapons[0]
    )
}

fun String.getIdFromUrl() : String {
    return this.split("/").last()
}