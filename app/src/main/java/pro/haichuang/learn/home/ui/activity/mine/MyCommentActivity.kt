package pro.haichuang.learn.home.ui.activity.mine

import com.jacy.kit.adapter.CommonAdapter
import com.jacy.kit.config.mStartActivity
import kotlinx.android.synthetic.main.activity_my_dynamic.*
import kotlinx.android.synthetic.main.item_mine_comment.view.*
import pro.haichuang.learn.home.R
import pro.haichuang.learn.home.annotation.ContentView
import pro.haichuang.learn.home.config.BaseActivity
import pro.haichuang.learn.home.ui.activity.find.FindDetailsActivity


@ContentView(R.layout.activity_my_comment)
class MyCommentActivity : BaseActivity() {

    override fun initData() {
        titleModel.title = "评论"
        listView.adapter = CommonAdapter(layoutInflater, R.layout.item_mine_comment, arrayListOf(1, 2, 3, 4, 56, 78, 9)) { v, _, _ ->
            v.to_details.setOnClickListener {
                mStartActivity(FindDetailsActivity::class.java)
            }
        }
    }

}
