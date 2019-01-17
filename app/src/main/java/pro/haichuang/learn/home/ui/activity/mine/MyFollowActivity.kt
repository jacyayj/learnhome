package pro.haichuang.learn.home.ui.activity.mine

import com.jacy.kit.adapter.CommonAdapter
import kotlinx.android.synthetic.main.activity_my_follow.*
import pro.haichuang.learn.home.R
import pro.haichuang.learn.home.annotation.ContentView
import pro.haichuang.learn.home.config.BaseActivity
import pro.haichuang.learn.home.ui.activity.mine.itemmodel.FollowModel


@ContentView(R.layout.activity_my_follow)
class MyFollowActivity : BaseActivity() {

    override fun initData() {
        titleModel.title = "我的关注"
        listView.adapter = CommonAdapter(layoutInflater, R.layout.item_mine_follow, arrayListOf(FollowModel(), FollowModel(), FollowModel(), FollowModel(), FollowModel(), FollowModel()))
    }

}
