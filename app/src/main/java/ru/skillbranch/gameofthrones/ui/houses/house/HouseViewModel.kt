package ru.skillbranch.gameofthrones.ui.houses.house

import androidx.lifecycle.*
import ru.skillbranch.gameofthrones.data.local.entities.CharacterItem
import ru.skillbranch.gameofthrones.data.local.entities.HouseType
import ru.skillbranch.gameofthrones.repositories.RootRepository
import java.lang.IllegalArgumentException

class HouseViewModel(val houseType: HouseType) : ViewModel() {

    private val _fullCharacterList = MutableLiveData<List<CharacterItem>>()

    private val _characterList = MediatorLiveData<List<CharacterItem>>()
    val characterList: LiveData<List<CharacterItem>>
        get() = _characterList

    init {
        _characterList.addSource(_fullCharacterList) {
            _characterList.postValue(it)
        }
        loadCharactersList()
    }

    private fun loadCharactersList() {
        RootRepository.findCharactersByHouseName(houseType.shortName) { list ->
            _fullCharacterList.postValue(list.sortedBy { it.name })
        }
    }

    fun handleSearch(query: String?) {
        if (query == null) return
        _characterList.postValue(_fullCharacterList.value?.filter {
            it.name.contains(query, true)
        })
    }

    class ModelFactory(private val houseType: HouseType) : ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass == (HouseViewModel::class.java)) {
                return HouseViewModel(houseType) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}