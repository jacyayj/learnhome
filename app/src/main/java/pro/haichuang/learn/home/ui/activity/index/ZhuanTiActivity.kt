package pro.haichuang.learn.home.ui.activity.index

import com.jacy.kit.adapter.CommonAdapter
import com.jacy.kit.config.mStartActivity
import kotlinx.android.synthetic.main.activity_zhuanti.*
import pro.haichuang.learn.home.R
import pro.haichuang.learn.home.annotation.ContentView
import pro.haichuang.learn.home.config.BaseActivity


@ContentView(R.layout.activity_zhuanti)
class ZhuanTiActivity : BaseActivity() {

    override fun initData() {
        titleModel.title = "线下讲座"
        listView.adapter = CommonAdapter(layoutInflater,R.layout.item_zhuanti, arrayListOf(1,2,3,4,5))
    }

    override fun initListener() {
        listView.setOnItemClickListener { _, _, position, _ ->
            mStartActivity(ZhuanTiDetailsActivity::class.java)
        }
    }
}
