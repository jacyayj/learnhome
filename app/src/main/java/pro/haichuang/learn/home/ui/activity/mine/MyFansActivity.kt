package pro.haichuang.learn.home.ui.activity.mine

import com.jacy.kit.adapter.CommonAdapter
import com.jacy.kit.config.ContentView
import com.jacy.kit.config.gone
import com.jacy.kit.config.show
import com.jacy.kit.config.toast
import com.zhouyou.http.model.HttpParams
import kotlinx.android.synthetic.main.activity_my_fans.*
import kotlinx.android.synthetic.main.item_mine_fans.view.*
import pro.haichuang.learn.home.R
import pro.haichuang.learn.home.config.BaseActivity
import pro.haichuang.learn.home.net.Url
import pro.haichuang.learn.home.ui.activity.mine.itemmodel.FansModel
import pro.haichuang.learn.home.utils.GsonUtil


@ContentView(R.layout.activity_my_fans)
class MyFansActivity : BaseActivity() {

    private val adapter by lazy {
        CommonAdapter<FansModel>(layoutInflater, R.layout.item_mine_fans) { v, t, _ ->
            v.do_follow.setOnClickListener {
                val params = HttpParams()
                params.put("attentionUserId", t.id.toString())
                params.put("operate", if (t.hasAttention) "0" else "1")
                post(Url.Friend.Attention, params, needSession = true) {
                    t.hasAttention = t.hasAttention.not()
                    toast(if (t.hasAttention) "关注成功" else "取消关注成功")
                }
            }
        }
    }

    override fun initData() {
        titleModel.title = "我的粉丝"
        pageUrl = Url.Friend.MyFans
        listView.adapter = adapter
        fetchPageData()
    }

    override fun onSuccess(url: String, result: Any?) {
        when (url) {
            Url.Friend.MyFans -> {
                GsonUtil.parseRows(result, FansModel::class.java).list?.let {
                    if (!isLoadMore && it.isEmpty())
                        no_fans.show()
                    else {
                        no_fans.gone()
                        dealRows(adapter, it)
                    }
                }
            }
        }
    }
}
