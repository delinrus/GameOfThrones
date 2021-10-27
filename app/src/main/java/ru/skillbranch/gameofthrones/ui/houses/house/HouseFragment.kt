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


class HouseFragment : Fragment() {

    private lateinit var viewModel: HouseViewModel
    private lateinit var house: HouseType

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseParams()
    }

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
        val adapter = CharactersListAdapter(house)
        recyclerView.adapter = adapter

        viewModel.characterList.observe(viewLifecycleOwner) {
            adapter.characters = it
        }
        viewModel.loadCharactersList(house)
    }

    private fun parseParams() {
        val args = requireArguments()
        house = args.getSerializable(HOUSE_TYPE) as HouseType
    }

    companion object {

        private const val HOUSE_TYPE = "house_type"

        fun newInstance(house: HouseType): HouseFragment {
            return HouseFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(HOUSE_TYPE, house)
                }
            }
        }
    }
}