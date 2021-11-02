package ru.skillbranch.gameofthrones.ui.houses.house

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import ru.skillbranch.gameofthrones.R
import ru.skillbranch.gameofthrones.data.local.entities.HouseType
import ru.skillbranch.gameofthrones.extensions.hideKeyboard
import ru.skillbranch.gameofthrones.ui.houses.HousesFragmentDirections


class HouseFragment : Fragment() {

    private lateinit var viewModel: HouseViewModel
    private lateinit var house: HouseType

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        parseParams()
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        val menuItem = menu.findItem(R.id.action_search)
        val searchView = (menuItem.actionView as SearchView)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                handleSearch(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                handleSearch(newText)
                return true
            }
        })
        super.onPrepareOptionsMenu(menu)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_house, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(this, HouseViewModel.ModelFactory(house))
            .get(HouseViewModel::class.java)
        prepareRecyclerView(view.findViewById(R.id.recycler))
    }

    private fun prepareRecyclerView(recyclerView: RecyclerView) {
        val adapter = CharactersListAdapter(house)
        recyclerView.adapter = adapter

        adapter.onCharacterItemClickListener = {
            context?.hideKeyboard(requireView())
            findNavController().navigate(
                HousesFragmentDirections.actionHousesFragmentToCharacterFragment(it.id)
            )
        }

        viewModel.characterList.observe(viewLifecycleOwner) {
            adapter.characters = it
        }
    }

    private fun handleSearch(query: String?) {
        viewModel.handleSearch(query)
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