package com.wireless.stickie.Adapter

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.wireless.stickie.Common.Common.fragmentList
import com.wireless.stickie.QuestionFragment
import java.lang.StringBuilder

class MyFragmentAdapter(
    fm: FragmentManager,
    var context: Context,
    fragmentList: List<QuestionFragment>
) : FragmentPagerAdapter(fm) {
    internal var instance: MyFragmentAdapter? = null
    override fun getItem(p0: Int): Fragment {
        return fragmentList[p0]
    }

    override fun getCount(): Int {
        return fragmentList.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return StringBuilder("Question ").append(position + 1).toString()
    }
}