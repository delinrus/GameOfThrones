package ru.skillbranch.gameofthrones.network

data class Post(
    var userId: Int,
    var id: Int,
    var title: String,
    var body: String
)
