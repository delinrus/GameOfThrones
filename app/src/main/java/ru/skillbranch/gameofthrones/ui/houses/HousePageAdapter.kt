package ru.skillbranch.gameofthrones.ui.houses

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import ru.skillbranch.gameofthrones.data.local.entities.HouseType
import ru.skillbranch.gameofthrones.ui.houses.house.HouseFragment

class HousePageAdapter(fm: FragmentManager) :
    FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getCount(): Int {
        return HouseType.values().size
    }

    override fun getItem(position: Int): Fragment {
        val fragment = HouseFragment.newInstance(HouseType.values()[position])
        return fragment
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return HouseType.values()[position].shortName
    }
}