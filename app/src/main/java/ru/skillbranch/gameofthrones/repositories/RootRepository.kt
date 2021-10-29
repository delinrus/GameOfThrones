package ru.skillbranch.gameofthrones.repositories

import androidx.annotation.VisibleForTesting
import kotlinx.coroutines.*
import ru.skillbranch.gameofthrones.App
import ru.skillbranch.gameofthrones.AppConfig
import ru.skillbranch.gameofthrones.data.local.AppDatabase
import ru.skillbranch.gameofthrones.data.local.entities.*
import ru.skillbranch.gameofthrones.data.remote.NetworkService
import ru.skillbranch.gameofthrones.data.remote.res.CharacterRes
import ru.skillbranch.gameofthrones.data.remote.res.HouseRes
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

object RootRepository {

    private val webServiceApi = NetworkService.getWebServiceApi()
    private val scope = CoroutineScope(Dispatchers.IO)
    private val db = AppDatabase.getInstance(App.applicationContext())

    /**
     * Получение данных о всех домах из сети
     * @param result - колбек содержащий в себе список данных о домах
     */
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun getAllHouses(result: (houses: List<HouseRes>) -> Unit) {
        scope.launch {
            val houses = mutableListOf<HouseRes>()
            for (page in 1..1000) {
                val deferredData = webServiceApi.getHouses(page).await()
                if (deferredData.isEmpty()) break
                houses.addAll(deferredData)
            }
            result(houses)
        }
    }

    /**
     * Получение данных о требуемых домах по их полным именам из сети
     * @param houseNames - массив полных названий домов (смотри AppConfig)
     * @param result - колбек содержащий в себе список данных о домах
     */
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun getNeedHouses(vararg houseNames: String, result: (houses: List<HouseRes>) -> Unit) {
        scope.launch {
            val houses = mutableListOf<HouseRes>()
            houseNames.forEach {
                val deferredData = webServiceApi.getHouseByName(it).await()
                houses.addAll(deferredData)
            }
            result(houses)
        }
    }

    /**
     * Получение данных о требуемых домах по их полным именам и персонажах в каждом из домов из сети
     * @param houseNames - массив полных названий домов (смотри AppConfig)
     * @param result - колбек содержащий в себе список данных о доме и персонажей в нем (Дом - Список Персонажей в нем)
     */
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun getNeedHouseWithCharacters(
        vararg houseNames: String,
        result: (houses: List<Pair<HouseRes, List<CharacterRes>>>) -> Unit
    ) {
        scope.launch {
            val housesWithCharacters = mutableListOf<Pair<HouseRes, List<CharacterRes>>>()
            val houses = mutableListOf<HouseRes>()
            suspendCoroutine<Unit> { continuation ->
                getNeedHouses(*houseNames) {
                    houses.addAll(it)
                    continuation.resume(Unit)
                }
            }
            houses.forEach { house ->
                val charactersDeterred = mutableListOf<Deferred<CharacterRes>>()
                house.swornMembers.forEach { member ->
                    val characterId = member.split("/").last().toInt()
                    val deferredCharacter = webServiceApi.getCharacterByID(characterId)
                    charactersDeterred.add(deferredCharacter)
                }
                val characters = charactersDeterred.awaitAll()
                characters.forEach {
                    it.houseId = HouseRes.parseShortName(house.name)
                }
                housesWithCharacters.add(house to characters)
            }
            result(housesWithCharacters)
        }
    }

    /**
     * Запись данных о домах в DB
     * @param houses - Список персонажей (модель HouseRes - модель ответа из сети)
     * необходимо произвести трансформацию данных
     * @param complete - колбек о завершении вставки записей db
     */
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun insertHouses(houses: List<HouseRes>, complete: () -> Unit) {
        scope.launch {
            db.getHouseDao().insertAll(houses.map { it -> it.toHouse() })
            complete()
        }
    }

    /**
     * Запись данных о пересонажах в DB
     * @param Characters - Список персонажей (модель CharacterRes - модель ответа из сети)
     * необходимо произвести трансформацию данных
     * @param complete - колбек о завершении вставки записей db
     */
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun insertCharacters(characters: List<CharacterRes>, complete: () -> Unit) {
        scope.launch {
            db.getCharacterDao().insertAll(characters.map { it -> it.toCharacter() })
            complete()
        }
    }

    /**
     * При вызове данного метода необходимо выполнить удаление всех записей в db
     * @param complete - колбек о завершении очистки db
     */
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun dropDb(complete: () -> Unit) {
        scope.launch {
            db.clearAllTables()
            complete()
        }
    }

    /**
     * Поиск всех персонажей по имени дома, должен вернуть список краткой информации о персонажах
     * дома - смотри модель CharacterItem
     * @param name - краткое имя дома (его первычный ключ)
     * @param result - колбек содержащий в себе список краткой информации о персонажах дома
     */
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun findCharactersByHouseName(name: String, result: (characters: List<CharacterItem>) -> Unit) {
        scope.launch {
            result(db.getCharacterDao().getByHouse(name).map { it -> it.toCharacterItem() })
        }
    }

    /**
     * Поиск персонажа по его идентификатору, должен вернуть полную информацию о персонаже
     * и его родственных отношения - смотри модель CharacterFull
     * @param id - идентификатор персонажа
     * @param result - колбек содержащий в себе полную информацию о персонаже
     */
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun findCharacterFullById(id: String, result: (character: CharacterFull) -> Unit) {
        scope.launch {
            val character = db.getCharacterDao().getById(id)
            val house = db.getHouseDao().getById(character.houseId)
            val father = if (character.father.isNotBlank())
                db.getCharacterDao().getById(character.father).toRelativeCharacter()
            else null

            val mother = if (character.mother.isNotBlank())
                db.getCharacterDao().getById(character.mother).toRelativeCharacter()
            else null
            result(character.toCharacterFull(house.words, father, mother))
        }
    }

    /**
     * Метод возвращет true если в базе нет ни одной записи, иначе false
     * @param result - колбек о завершении очистки db
     */
    fun isNeedUpdate(result: (isNeed: Boolean) -> Unit) {
        scope.launch {
            val countOfRecords = db.getHouseDao().count() + db.getCharacterDao().count()
            result(countOfRecords == 0)
        }
    }

    suspend fun isUpToDate(): Boolean {
        return suspendCoroutine { continuation ->
            isNeedUpdate() {
                continuation.resume(!it)
            }
        }
    }

    // Only for test purpose since the server not provides information about parent
    fun updateSomeCharactersToHaveParents(complete: () -> Unit) {
        scope.launch {
            val aria = db.getCharacterDao().getById("148")
            db.getCharacterDao().insert(aria.copy(mother = "16", father = "206"))
            complete()
        }
    }

    suspend fun sync() {
        suspendCoroutine<Unit> { continuation ->
            getNeedHouseWithCharacters(*AppConfig.NEED_HOUSES) {
                val (houses, characters) = it.unzip()
                insertHouses(houses) {
                    insertCharacters(characters.flatten()) {
                        updateSomeCharactersToHaveParents() {
                            continuation.resume(Unit)
                        }
                    }
                }
            }
        }
    }
}