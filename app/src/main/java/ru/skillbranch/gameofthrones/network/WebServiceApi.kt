package ru.skillbranch.gameofthrones.network

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ru.skillbranch.gameofthrones.data.remote.res.CharacterRes
import ru.skillbranch.gameofthrones.data.remote.res.HouseRes

interface WebServiceApi {
    @GET("characters/{id}")
    fun getCharacterByID(@Path("id") id: Int): Call<CharacterRes>

    @GET("houses")
    fun getHouseByName(@Query("name") name: String): Call<List<HouseRes>>
}