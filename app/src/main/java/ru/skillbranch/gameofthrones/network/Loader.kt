package ru.skillbranch.gameofthrones.network

import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.*
import ru.skillbranch.gameofthrones.AppConfig.NEED_HOUSES
import ru.skillbranch.gameofthrones.data.remote.res.CharacterRes
import ru.skillbranch.gameofthrones.data.remote.res.HouseRes

object Loader {
    var houses = mutableListOf<HouseRes>()
    var characters = mutableListOf<CharacterRes>()
    val isFinished = MutableLiveData<Boolean>()


    private suspend fun loadHouses() {
        val result = mutableListOf<Deferred<List<HouseRes>>>()
        NEED_HOUSES.forEach {
            result.add(NetworkService.getWebServiceApi().getHouseByName(it))
        }
        houses.addAll(result.awaitAll().map { it[0] })
    }

    private suspend fun loadCharacters() {
        val result = mutableListOf<Deferred<CharacterRes>>()
        houses.forEach { house ->
            house.swornMembers.forEach { member ->
                val characterId = member.split("/").last().toInt()
                val deferredCharacter =
                    NetworkService.getWebServiceApi().getCharacterByID(characterId)
                result.add(deferredCharacter)
            }
        }
        characters.addAll(result.awaitAll())
    }

    fun load() {
        CoroutineScope(Dispatchers.IO).launch {
            loadHouses()
            loadCharacters()
            isFinished.postValue(true)
        }
    }
}