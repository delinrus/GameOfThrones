package ru.skillbranch.gameofthrones.data.local.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(
    foreignKeys = [ForeignKey(
        entity = House::class,
        parentColumns = ["id"],
        childColumns = ["houseId"]
    )]
)
data class Character(
    @PrimaryKey
    val id: String,
    val name: String,
    val gender: String,
    val culture: String,
    val born: String,
    val died: String,
    val titles: List<String> = listOf(),
    val aliases: List<String> = listOf(),
    val father: String, //rel
    val mother: String, //rel
    val spouse: String,
    val houseId: String//rel
) {

    fun toCharacterItem() =
        CharacterItem(id = id, house = houseId, name = name, titles = titles, aliases = aliases)

    fun toRelativeCharacter() =
        RelativeCharacter(id = id, house = houseId, name = name)

    fun toCharacterFull(
        words: String,
        father: RelativeCharacter,
        mother: RelativeCharacter
    ) = CharacterFull(
        id = id,
        name = name,
        words = words,
        born = born,
        titles = titles,
        died = died,
        aliases = aliases,
        house = houseId,
        father = father,
        mother = mother
    )
}

data class CharacterItem(
    val id: String,
    val house: String, //rel
    val name: String,
    val titles: List<String>,
    val aliases: List<String>
) : Serializable

data class CharacterFull(
    val id: String,
    val name: String,
    val words: String,
    val born: String,
    val died: String,
    val titles: List<String>,
    val aliases: List<String>,
    val house: String, //rel
    val father: RelativeCharacter?,
    val mother: RelativeCharacter?
)

data class RelativeCharacter(
    val id: String,
    val name: String,
    val house: String //rel
)