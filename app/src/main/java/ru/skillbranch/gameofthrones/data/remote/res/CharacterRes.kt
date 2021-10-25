package ru.skillbranch.gameofthrones.data.remote.res

import ru.skillbranch.gameofthrones.data.local.entities.Character
import ru.skillbranch.gameofthrones.extensions.getIdFromUrl

data class CharacterRes(
    val url: String,
    val name: String,
    val gender: String,
    val culture: String,
    val born: String,
    val died: String,
    val titles: List<String> = listOf(),
    val aliases: List<String> = listOf(),
    val father: String,
    val mother: String,
    val spouse: String,
    val allegiances: List<String> = listOf(),
    val books: List<String> = listOf(),
    val povBooks: List<Any> = listOf(),
    val tvSeries: List<String> = listOf(),
    val playedBy: List<String> = listOf()
) {
    var houseId: String = ""

    fun toCharacter() = Character(
        id = url.getIdFromUrl(),
        name = name,
        gender = gender,
        culture = culture,
        born = born,
        died = died,
        titles = titles,
        aliases = aliases,
        father = father.getIdFromUrl(),
        mother = mother.getIdFromUrl(),
        spouse = spouse.getIdFromUrl(),
        houseId = houseId
    )
}
