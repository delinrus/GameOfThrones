package ru.skillbranch.gameofthrones.data.local

import androidx.room.TypeConverter

class StringListConverter {

    @TypeConverter
    fun fromList(list: List<String>) : String {
        var result = StringBuilder()
        list.forEach {
            result.append(it)
            result.append(";")
        }
        return result.toString()
    }

    @TypeConverter
    fun toList(str: String) : List<String> {
        return str.split(";").filter { it -> it.isNotBlank() }
    }
}