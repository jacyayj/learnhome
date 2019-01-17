package pro.haichuang.learn.home.ui.activity.message

import com.jacy.kit.adapter.CommonAdapter
import com.jacy.kit.config.mStartActivity
import kotlinx.android.synthetic.main.activity_friend_search.*
import pro.haichuang.learn.home.R
import pro.haichuang.learn.home.annotation.ContentView
import pro.haichuang.learn.home.config.BaseActivity


@ContentView(R.layout.activity_friend_search)
class FriendSearchActivity : BaseActivity() {

    override fun initData() {
        listView.adapter = CommonAdapter(layoutInflater, R.layout.item_search_friend, arrayListOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10))
    }

    override fun initListener() {
        listView.setOnItemClickListener { _, _, position, _ ->
            mStartActivity(FriendSettingActivity::class.java)
        }
    }
}
