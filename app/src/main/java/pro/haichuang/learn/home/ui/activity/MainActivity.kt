package pro.haichuang.learn.home.ui.activity

import android.support.v4.app.FragmentPagerAdapter
import com.jacy.kit.config.ContentView
import com.jacy.kit.config.mStartActivity
import com.jacy.kit.config.toast
import kotlinx.android.synthetic.main.activity_main.*
import pro.haichuang.learn.home.R
import pro.haichuang.learn.home.config.BaseActivity
import pro.haichuang.learn.home.ui.activity.login.LoginActivity
import pro.haichuang.learn.home.ui.activity.mine.FileActivity
import pro.haichuang.learn.home.ui.fragment.*
import pro.haichuang.learn.home.utils.SPUtils

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
                R.id.message ->
                    if (SPUtils.isTourist) {
                        when (pager.currentItem) {
                            0 -> navigation.post { navigation.selectedItemId = R.id.index }
                            1 -> navigation.post { navigation.selectedItemId = R.id.find }
                            3 -> navigation.post { navigation.selectedItemId = R.id.news }
                        }
                        mStartActivity(LoginActivity::class.java, Pair("re_login", true))
                        toast("请先登录后再操作")
                    } else
                        pager.currentItem = 2
                R.id.news -> pager.currentItem = 3
                R.id.mine ->
                    if (SPUtils.isTourist) {
                        when (pager.currentItem) {
                            0 -> navigation.post { navigation.selectedItemId = R.id.index }
                            1 -> navigation.post { navigation.selectedItemId = R.id.find }
                            3 -> navigation.post { navigation.selectedItemId = R.id.news }
                        }
                        mStartActivity(LoginActivity::class.java, Pair("re_login", true))
                        toast("请先登录后再操作")
                    } else
                        pager.currentItem = 4
            }
            true
        }
        if (SPUtils.isRegister)
            mStartActivity(FileActivity::class.java)
    }
}
