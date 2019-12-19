package com.ksballetba.rayplus.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager2.adapter.FragmentStateAdapter


class ViewPagerAdapter(var mList: List<Fragment>, fm: FragmentManager?) : FragmentStatePagerAdapter(fm!!,BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    override fun getItem(position: Int): Fragment {
        return mList[position]
    }

    override fun getCount(): Int {
        return mList.size
    }

    fun getFragmentByIdx(idx:Int):Fragment{
        return mList[idx]
    }

}