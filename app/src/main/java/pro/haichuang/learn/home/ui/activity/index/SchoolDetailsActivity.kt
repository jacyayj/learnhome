package pro.haichuang.learn.home.ui.activity.index

import android.support.design.widget.TabLayout
import com.jacy.kit.config.ContentView
import com.jacy.kit.config.gone
import com.jacy.kit.config.show
import com.zhouyou.http.model.HttpParams
import kotlinx.android.synthetic.main.activity_school_details.*
import kotlinx.android.synthetic.main.layout_jianzhang.*
import pro.haichuang.learn.home.R
import pro.haichuang.learn.home.config.BaseActivity
import pro.haichuang.learn.home.config.Constants
import pro.haichuang.learn.home.net.Url


@ContentView(R.layout.activity_school_details)
class SchoolDetailsActivity : BaseActivity() {

    override fun initData() {
        titleModel.title = "高校详情"
        post(Url.College.Get, HttpParams().apply {
            put("id", intent.getIntExtra(Constants.SCHOOL_ID,-1).toString())
        })
    }

    override fun initListener() {
        tab.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(p0: TabLayout.Tab?) {
            }

            override fun onTabUnselected(p0: TabLayout.Tab?) {
            }

            override fun onTabSelected(p0: TabLayout.Tab?) {
                when (p0?.position) {
                    0 -> {
                        tab_view1.show()
                        tab_view2.gone()
                        jianzhang_view.gone()
                    }
                    1 -> {
                        tab_view1.gone()
                        tab_view2.show()
                        jianzhang_view.gone()
                    }
                    2 -> {
                        tab_view1.gone()
                        tab_view2.gone()
                        jianzhang_view.show()
                    }
                }
            }
        })
    }
}
