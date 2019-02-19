package pro.haichuang.learn.home.ui.activity.mine

import com.jacy.kit.adapter.CommonAdapter
import kotlinx.android.synthetic.main.activity_my_follow.*
import pro.haichuang.learn.home.R
import com.jacy.kit.config.ContentView
import pro.haichuang.learn.home.config.DataBindingActivity
import pro.haichuang.learn.home.ui.activity.mine.itemmodel.FansModel
import pro.haichuang.learn.home.ui.activity.mine.viewmodel.MyFansModel


@ContentView(R.layout.activity_my_fans)
class MyFansActivity : DataBindingActivity<MyFansModel>() {
    override fun initData() {
        titleModel.title = "我的粉丝"
        listView.adapter = CommonAdapter(layoutInflater, R.layout.item_mine_fans, arrayListOf(FansModel(), FansModel(), FansModel(), FansModel(), FansModel(), FansModel()))
    }

}
