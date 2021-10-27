package ru.skillbranch.gameofthrones.ui.houses

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.activityViewModels
import androidx.viewpager.widget.ViewPager
import ru.skillbranch.gameofthrones.R
import ru.skillbranch.gameofthrones.data.local.entities.HouseType
import ru.skillbranch.gameofthrones.databinding.FragmentHousesBinding
import ru.skillbranch.gameofthrones.ui.houses.house.HouseViewModel


class HousesFragment : Fragment() {

    private lateinit var myContext: FragmentActivity

    private val viewModel: HouseViewModel by activityViewModels()

    private var _binding: FragmentHousesBinding? = null
    private val binding
        get() = _binding ?: throw RuntimeException("FragmentHousesBinding == null")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHousesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onAttach(activity: Activity) {
        myContext = activity as FragmentActivity
        super.onAttach(activity)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbar.inflateMenu(R.menu.menu_search)
        val menuItem = binding.toolbar.menu.findItem(R.id.action_search)
        val searchView = (menuItem.actionView as SearchView)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                viewModel.handleSearch(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel.handleSearch(newText)
                return true
            }
        })

        val pageAdapter = HousePageAdapter(myContext.supportFragmentManager)
        binding.pager.adapter = pageAdapter
        binding.tabs.setupWithViewPager(binding.pager)
        val pageListener = PagerListener()
        binding.pager.addOnPageChangeListener(pageListener)
        pageListener.onPageSelected(0)
    }

    inner class PagerListener : ViewPager.OnPageChangeListener {
        override fun onPageScrolled(
            position: Int,
            positionOffset: Float,
            positionOffsetPixels: Int
        ) {
        }

        override fun onPageSelected(position: Int) {
            binding.appBarLayout.setBackgroundColor(
                context?.getColor(HouseType.values()[position].colorRes) ?: 0
            )
            viewModel.loadCharactersList(HouseType.values()[position])
        }

        override fun onPageScrollStateChanged(state: Int) {
        }
    }
}