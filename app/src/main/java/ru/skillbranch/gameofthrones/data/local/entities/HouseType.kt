package ru.skillbranch.gameofthrones.data.local.entities

import ru.skillbranch.gameofthrones.R

enum class HouseType(val shortName: String, val imageRes: Int, val colorRes: Int) {

    STARK("Stark", R.drawable.stark_icon, R.color.stark_primary),
    LANNISTER("Lannister", R.drawable.lanister_icon, R.color.lannister_primary),
    TARGARYEN("Targaryen", R.drawable.targaryen_icon, R.color.targaryen_primary),
    BARATHEON("Baratheon", R.drawable.baratheon_icon, R.color.baratheon_primary),
    GREYJOY("Greyjoy", R.drawable.greyjoy_icon, R.color.greyjoy_primary),
    NUMERO_MARTELL("Martell", R.drawable.martel_icon, R.color.martel_primary),
    TYRELL("Tyrell", R.drawable.tyrel_icon, R.color.tyrel_primary)

}