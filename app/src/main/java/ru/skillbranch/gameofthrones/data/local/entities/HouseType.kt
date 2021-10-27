package ru.skillbranch.gameofthrones.data.local.entities

import ru.skillbranch.gameofthrones.R

enum class HouseType(val shortName: String, val imageRes: Int) {

    STARK("Stark", R.drawable.stark_icon),
    LANNISTER("Lannister", R.drawable.lanister_icon),
    TARGARYEN("Targaryen", R.drawable.targaryen_icon),
    BARATHEON("Baratheon", R.drawable.baratheon_icon),
    GREYJOY("Greyjoy", R.drawable.greyjoy_icon),
    NUMERO_MARTELL("Martell", R.drawable.martel_icon),
    TYRELL("Tyrell", R.drawable.tyrel_icon)

}