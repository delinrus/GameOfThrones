package ru.skillbranch.gameofthrones.extensions

fun String.getIdFromUrl() : String {
    return this.split("/").last()
}