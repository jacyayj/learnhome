package pro.haichuang.learn.home.ui.activity.mine

import com.jacy.kit.adapter.CommonRecyclerAdapter
import com.jacy.kit.config.*
import com.zhouyou.http.model.HttpParams
import kotlinx.android.synthetic.main.activity_my_follow.*
import kotlinx.android.synthetic.main.item_mine_follow.view.*
import pro.haichuang.learn.home.R
import pro.haichuang.learn.home.config.BaseActivity
import pro.haichuang.learn.home.net.Url
import pro.haichuang.learn.home.ui.activity.find.PersonalIndexActivity
import pro.haichuang.learn.home.ui.activity.mine.itemmodel.FollowModel
import pro.haichuang.learn.home.utils.GsonUtil


@ContentView(R.layout.activity_my_follow)
class MyFollowActivity : BaseActivity() {

    private val adapter by lazy {
        CommonRecyclerAdapter<FollowModel>(layoutInflater, R.layout.item_mine_follow, ArrayList()) { v, t, _ ->
            t.follow = hasFollow
            v.do_follow.setOnClickListener {
                val params = HttpParams()
                params.put("attentionUserId", t.id.toString())
                params.put("operate", if (t.follow) "0" else "1")
                post(Url.Friend.Attention, params, needSession = true) {
                    t.follow = t.follow.not()
                    toast(if (t.follow) "关注成功" else "取消关注成功")
                    notifyHasFollow()
                }
            }
        }
    }

    private var hasFollow = true

    override fun initData() {
        titleModel.title = "我的关注"
        pageUrl = Url.Friend.MyAttention
        fetchPageData()
    }

    override fun onSuccess(url: String, result: Any?) {
        when (url) {
            Url.Friend.MyAttention -> {
                val rows = GsonUtil.parseRows(result, FollowModel::class.java)
                rows.list?.let {
                    if (it.isEmpty()) {
                        refresh_layout.setEnableLoadMore(false)
                        no_friend.show()
                        recommend_friend.show()
                        hasFollow = false
                        post(Url.Friend.Recommend)
                    } else {
                        refresh_layout.setEnableLoadMore(true)
                        no_friend.gone()
                        recommend_friend.gone()
                        hasFollow = true
                        dealRows(adapter, it)
                    }
                }
            }
            Url.Friend.Recommend -> {
                val array = GsonUtil.parseArray(result, FollowModel::class.java)
                adapter.refresh(array)

            }
        }
    }

    override fun initListener() {
        listView.setSwipeMenuItemClickListener { _, position ->
            mStartActivity(PersonalIndexActivity::class.java)
        }
        listView.adapter = adapter
    }

    private fun notifyHasFollow() {
        hasFollow = adapter.data.find { it.follow } != null
    }
}
