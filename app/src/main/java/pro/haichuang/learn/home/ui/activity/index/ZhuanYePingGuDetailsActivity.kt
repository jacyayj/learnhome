package pro.haichuang.learn.home.ui.activity.index

import com.jacy.kit.adapter.CommonAdapter
import kotlinx.android.synthetic.main.activity_zhuanye_pinggu_details.*
import pro.haichuang.learn.home.annotation.ContentView
import pro.haichuang.learn.home.config.BaseActivity
import pro.haichuang.learn.home.R


@ContentView(R.layout.activity_zhuanye_pinggu_details)
class ZhuanYePingGuDetailsActivity : BaseActivity() {

    override fun initData() {
        titleModel.title = "专业评估详情"
        listView.adapter = CommonAdapter(layoutInflater,R.layout.item_zhuanye_pinggu_details, arrayListOf(1,2,3,4,5,6,7,8))
    }

}
