package ru.skillbranch.gameofthrones.ui.houses

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager.widget.ViewPager
import ru.skillbranch.gameofthrones.R
import ru.skillbranch.gameofthrones.data.local.entities.HouseType
import ru.skillbranch.gameofthrones.databinding.FragmentHousesBinding
import ru.skillbranch.gameofthrones.ui.houses.house.HouseFragment


class HousesFragment : Fragment() {

    private lateinit var myContext: FragmentActivity

    private var _binding: FragmentHousesBinding? = null
    private val binding
        get() = _binding ?: throw RuntimeException("FragmentHousesBinding == null")

    private lateinit var pageAdapter: HousePageAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pageAdapter = HousePageAdapter(childFragmentManager)
    }

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
                getCurrentHouseFragment()?.handleSearch(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                getCurrentHouseFragment()?.handleSearch(newText)
                return true
            }
        })
        binding.pager.adapter = pageAdapter
        binding.tabs.setupWithViewPager(binding.pager)
        val pageListener = PagerListener()
        binding.pager.addOnPageChangeListener(pageListener)
        pageListener.onPageSelected(0)
    }

    private fun getCurrentHouseFragment(): HouseFragment? {
        return pageAdapter.getRegisteredFragment(binding.pager.currentItem)
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
                context?.getColor(HouseType.values()[position].colorPrimaryRes) ?: 0
            )
        }

        override fun onPageScrollStateChanged(state: Int) {
        }
    }
}