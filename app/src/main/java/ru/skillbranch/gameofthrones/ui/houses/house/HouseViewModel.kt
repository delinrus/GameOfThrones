package ru.skillbranch.gameofthrones.ui.houses.house

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.skillbranch.gameofthrones.data.local.entities.CharacterItem
import ru.skillbranch.gameofthrones.data.local.entities.HouseType
import ru.skillbranch.gameofthrones.repositories.RootRepository

class HouseViewModel: ViewModel() {

    private val _characterList = MutableLiveData<List<CharacterItem>>()
    val characterList: LiveData<List<CharacterItem>>
        get() = _characterList

    fun loadCharactersList(houseType: HouseType) {
        RootRepository.findCharactersByHouseName(houseType.shortName) {
            _characterList.value = it
        }
    }
}