package ru.skillbranch.gameofthrones.ui.houses.house

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import ru.skillbranch.gameofthrones.R
import ru.skillbranch.gameofthrones.data.local.entities.HouseType


class HouseFragment(val house: HouseType) : Fragment() {

    private lateinit var viewModel: HouseViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_house, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[HouseViewModel::class.java]

        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler)
        val adapter = CharactersListAdapter()
        recyclerView.adapter = adapter

        viewModel.characterList.observe(viewLifecycleOwner){
            adapter.characters = it
        }
        viewModel.loadCharactersList(house)
    }
}