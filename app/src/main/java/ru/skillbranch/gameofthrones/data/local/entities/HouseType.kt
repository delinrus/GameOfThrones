package ru.skillbranch.gameofthrones.data.local.entities

import ru.skillbranch.gameofthrones.R

enum class HouseType(
    val shortName: String,
    val iconRes: Int,
    val colorPrimaryRes: Int,
    val colorAccentRes: Int,
    val coatOfArmsRes: Int
) {

    STARK(
        "Stark",
        R.drawable.stark_icon,
        R.color.stark_primary,
        R.color.stark_accent,
        R.drawable.stark_coast_of_arms
    ),
    LANNISTER(
        "Lannister",
        R.drawable.lanister_icon,
        R.color.lannister_primary,
        R.color.lannister_accent,
        R.drawable.lannister__coast_of_arms
    ),
    TARGARYEN(
        "Targaryen",
        R.drawable.targaryen_icon,
        R.color.targaryen_primary,
        R.color.targaryen_accent,
        R.drawable.targaryen_coast_of_arms
    ),
    BARATHEON(
        "Baratheon",
        R.drawable.baratheon_icon,
        R.color.baratheon_primary,
        R.color.baratheon_accent,
        R.drawable.baratheon_coast_of_arms
    ),
    GREYJOY(
        "Greyjoy",
        R.drawable.greyjoy_icon,
        R.color.greyjoy_primary,
        R.color.greyjoy_accent,
        R.drawable.greyjoy_coast_of_arms
    ),
    MARTELL(
        "Martell",
        R.drawable.martel_icon,
        R.color.martel_primary,
        R.color.martel_accent,
        R.drawable.martel_coast_of_arms
    ),
    TYRELL(
        "Tyrell",
        R.drawable.tyrel_icon,
        R.color.tyrel_primary,
        R.color.tyrel_accent,
        R.drawable.tyrel_coast_of_arms
    )
}