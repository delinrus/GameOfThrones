package ru.skillbranch.gameofthrones.data.remote.res

import ru.skillbranch.gameofthrones.data.local.entities.Character
import ru.skillbranch.gameofthrones.data.local.entities.House
import ru.skillbranch.gameofthrones.extensions.getIdFromUrl
import java.util.regex.Pattern

data class HouseRes(
    val url: String,
    val name: String,
    val region: String,
    val coatOfArms: String,
    val words: String,
    val titles: List<String> = listOf(),
    val seats: List<String> = listOf(),
    val currentLord: String,
    val heir: String,
    val overlord: String,
    val founded: String,
    val founder: String,
    val diedOut: String,
    val ancestralWeapons: List<String> = listOf(),
    val cadetBranches: List<Any> = listOf(),
    val swornMembers: List<String> = listOf()
) {
    fun toHouse() = House(
        id = parseShortName(name),
        name = name,
        region = region,
        coatOfArms = coatOfArms,
        words = words,
        titles = titles[0],
        seats = seats[0],
        currentLord = currentLord.getIdFromUrl(),
        heir = heir.getIdFromUrl(),
        overlord = overlord,
        founded = founded,
        founder = founder.getIdFromUrl(),
        diedOut = diedOut,
        ancestralWeapons = ancestralWeapons[0]
    )

    companion object {
        private val pattern by lazy { Pattern.compile("^House (.*) of") }

        fun parseShortName(str: String): String {
            val matcher = pattern.matcher(str)
            matcher.find()
            return matcher.group(1)?: str
        }
    }
}