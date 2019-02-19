package pro.haichuang.learn.home.ui.fragment

import android.support.design.widget.TabLayout
import com.jacy.kit.adapter.CommonAdapter
import com.jacy.kit.config.gone
import com.jacy.kit.config.mStartActivity
import com.jacy.kit.config.show
import kotlinx.android.synthetic.main.fragment_find.*
import pro.haichuang.learn.home.R
import com.jacy.kit.config.ContentView
import pro.haichuang.learn.home.config.BaseFragment
import pro.haichuang.learn.home.ui.activity.find.FindDetailsActivity
import pro.haichuang.learn.home.ui.activity.find.FindReleaseActivity

@ContentView(R.layout.fragment_find)
class FindFragment : BaseFragment() {

    override fun initData() {
        listView.adapter = CommonAdapter(layoutInflater, R.layout.item_find_first, arrayListOf(1, 2, 3, 4, 5, 6, 7))
    }

    override fun initListener() {
        tab.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(p0: TabLayout.Tab?) {
            }

            override fun onTabUnselected(p0: TabLayout.Tab?) {
            }

            override fun onTabSelected(p0: TabLayout.Tab?) {
                if (p0?.position == 0) {
                    ad_view.show()
                    to_release.show()
                    listView.adapter = CommonAdapter(layoutInflater, R.layout.item_find_first, arrayListOf(1, 2, 3, 4, 5, 6, 7))
                } else {
                    ad_view.gone()
                    to_release.gone()
                    listView.adapter = CommonAdapter(layoutInflater, R.layout.item_find_other, arrayListOf(1, 2, 3, 4, 5, 6, 7))
                }
            }
        })
        to_release.setOnClickListener {
            mStartActivity(FindReleaseActivity::class.java)
        }
        listView.setOnItemClickListener { _, _, position, _ ->
            mStartActivity(FindDetailsActivity::class.java)
        }
    }
}