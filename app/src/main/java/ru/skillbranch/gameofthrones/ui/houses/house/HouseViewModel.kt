package ru.skillbranch.gameofthrones.ui.houses.house

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.skillbranch.gameofthrones.data.local.entities.CharacterItem
import ru.skillbranch.gameofthrones.data.local.entities.HouseType
import ru.skillbranch.gameofthrones.repositories.RootRepository

class HouseViewModel : ViewModel() {

    private val _fullCharacterList = MutableLiveData<List<CharacterItem>>()

    private val _characterList = MediatorLiveData<List<CharacterItem>>()
    val characterList: LiveData<List<CharacterItem>>
        get() = _characterList

    private var houseType: HouseType? = null

    init {
        _characterList.addSource(_fullCharacterList) {
            _characterList.postValue(it)
        }
    }

    fun loadCharactersList(houseType: HouseType) {
        if (this.houseType == null || this.houseType != houseType) {
            this.houseType = houseType
            RootRepository.findCharactersByHouseName(houseType.shortName) {
                _fullCharacterList.postValue(it)
            }
        }
    }

    fun handleSearch(query: String?) {
        if (query == null) return
        _characterList.postValue(_fullCharacterList.value?.filter {
            it.name.contains(query, true)
        })
    }
}