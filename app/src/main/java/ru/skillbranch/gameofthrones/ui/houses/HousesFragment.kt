package ru.skillbranch.gameofthrones.ui.houses

import android.app.Activity
import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewAnimationUtils
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.animation.doOnEnd
import androidx.core.animation.doOnStart
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.google.android.material.tabs.TabLayout
import ru.skillbranch.gameofthrones.R
import ru.skillbranch.gameofthrones.data.local.entities.HouseType
import ru.skillbranch.gameofthrones.databinding.FragmentHousesBinding
import ru.skillbranch.gameofthrones.ui.houses.house.HouseFragment
import kotlin.math.hypot
import kotlin.math.max


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
        binding.tabs.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                val position = tab.position
                val rect = Rect()
                val tabView = tab.view as View

                tabView.postDelayed(
                    {
                        tabView.getGlobalVisibleRect(rect)
                        animateAppbarReveal(position, rect.centerX(), rect.centerY())
                    },
                    300
                )
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

        })
    }

    private fun animateAppbarReveal(position: Int, centerX: Int, centerY: Int) {
        val endRadius = max(
            hypot(centerX.toDouble(), centerY.toDouble()),
            hypot(binding.appBarLayout.width.toDouble() - centerX.toDouble(), centerY.toDouble())
        )
        binding.revealView.setBackgroundColor(
            context?.getColor(HouseType.values()[position].colorPrimaryRes) ?: 0
        )
        val anim = ViewAnimationUtils.createCircularReveal(
            binding.revealView,
            centerX,
            centerY,
            0f,
            endRadius.toFloat()
        )

        anim.doOnStart {
            binding.revealView.visibility = View.VISIBLE
        }

        anim.doOnEnd {
            binding.revealView.visibility = View.INVISIBLE
            binding.appBarLayout.setBackgroundColor(
                context?.getColor(HouseType.values()[position].colorPrimaryRes) ?: 0
            )
        }
        anim.start()
    }


    private fun getCurrentHouseFragment(): HouseFragment? {
        return pageAdapter.getRegisteredFragment(binding.pager.currentItem)
    }
}