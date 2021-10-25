package ru.skillbranch.gameofthrones.ui.houses

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import ru.skillbranch.gameofthrones.AppConfig
import ru.skillbranch.gameofthrones.data.remote.res.HouseRes
import ru.skillbranch.gameofthrones.ui.houses.house.HouseFragment

class HousePageAdapter(fm: FragmentManager) :
    FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getCount(): Int {
        return AppConfig.NEED_HOUSES.size
    }

    override fun getItem(position: Int): Fragment {
        return HouseFragment()
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return HouseRes.parseShortName(AppConfig.NEED_HOUSES[position])
    }
}