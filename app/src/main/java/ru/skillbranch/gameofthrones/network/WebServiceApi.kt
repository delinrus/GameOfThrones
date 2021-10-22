package ru.skillbranch.gameofthrones.network

import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ru.skillbranch.gameofthrones.data.remote.res.CharacterRes
import ru.skillbranch.gameofthrones.data.remote.res.HouseRes

interface WebServiceApi {
    @GET("characters/{id}")
    fun getCharacterByID(@Path("id") id: Int): Deferred<CharacterRes>

    @GET("houses")
    fun getHouseByName(@Query("name") name: String): Deferred<List<HouseRes>>

    @GET("houses")
    fun getHouses(
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int = 50
    ): Deferred<List<HouseRes>>
}