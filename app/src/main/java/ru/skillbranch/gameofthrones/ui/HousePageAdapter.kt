package ru.skillbranch.gameofthrones.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import ru.skillbranch.gameofthrones.AppConfig
import ru.skillbranch.gameofthrones.HouseFragment

class HousePageAdapter(fm: FragmentManager) :
    FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getCount(): Int {
        return AppConfig.NEED_HOUSES.size
    }

    override fun getItem(position: Int): Fragment {
        return HouseFragment()
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return position.toString()
    }
}