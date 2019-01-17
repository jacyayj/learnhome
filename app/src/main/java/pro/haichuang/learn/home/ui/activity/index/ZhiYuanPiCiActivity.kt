package pro.haichuang.learn.home.ui.activity.index

import android.view.View
import com.jacy.kit.config.mStartActivity
import pro.haichuang.learn.home.annotation.ContentView
import pro.haichuang.learn.home.config.BaseActivity
import pro.haichuang.learn.home.R


@ContentView(R.layout.activity_zhiyuan_pici)
class ZhiYuanPiCiActivity : BaseActivity(){


    override fun initData() {
        titleModel.title="选择批次"
    }

    fun toDetails(v: View?) {
        mStartActivity(ZhiYuanDetailsActivity::class.java)
    }
}
