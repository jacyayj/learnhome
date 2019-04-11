package pro.haichuang.learn.home.ui.activity

import android.support.v4.app.FragmentPagerAdapter
import com.jacy.kit.config.ContentView
import kotlinx.android.synthetic.main.activity_main.*
import pro.haichuang.learn.home.R
import pro.haichuang.learn.home.config.BaseActivity
import pro.haichuang.learn.home.ui.fragment.*

@ContentView(R.layout.activity_main)
class MainActivity : BaseActivity() {

    private val fragments by lazy {
        arrayListOf(IndexFragment(), FindFragment(), MessageFragment(), NewsFragment(), MineFragment())
    }

    override fun initData() {
        pager.adapter = object : FragmentPagerAdapter(supportFragmentManager) {
            override fun getItem(position: Int) = fragments[position]

            override fun getCount() = fragments.size
        }
        pager.offscreenPageLimit = 5
        navigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.index -> pager.currentItem = 0
                R.id.find -> pager.currentItem = 1
                R.id.message -> pager.currentItem = 2
                R.id.news -> pager.currentItem = 3
                R.id.mine -> pager.currentItem = 4
            }
            true
        }
    }
}
