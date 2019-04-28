package pro.haichuang.learn.home.ui.activity.message

import android.text.Editable
import android.text.TextWatcher
import com.jacy.kit.adapter.CommonAdapter
import com.jacy.kit.config.mStartActivity
import kotlinx.android.synthetic.main.activity_friend_search.*
import pro.haichuang.learn.home.R
import com.jacy.kit.config.ContentView
import com.zhouyou.http.model.HttpParams
import pro.haichuang.learn.home.config.BaseActivity
import pro.haichuang.learn.home.net.Url
import pro.haichuang.learn.home.ui.activity.message.itemmodel.FriendModel
import pro.haichuang.learn.home.utils.GsonUtil


@ContentView(R.layout.activity_friend_search)
class FriendSearchActivity : BaseActivity() {

    private val adapter by lazy { CommonAdapter<FriendModel>(layoutInflater, R.layout.item_search_friend) }

    private var queryName = ""

    override fun initData() {
        pageUrl = Url.Friend.Search
        listView.adapter = adapter
    }

    override fun setPageParams(pageParams: HttpParams) {
        pageParams.put("queryName", queryName)
    }

    override fun onSuccess(url: String, result: Any?) {
        when (url) {
            Url.Friend.Search -> {
                GsonUtil.parseRows(result, FriendModel::class.java).list?.let {
                    dealRows(adapter, it)
                }
            }
        }
    }

    override fun initListener() {
        clear.setOnClickListener {
            search_input.setText("")
        }
        listView.setOnItemClickListener { _, _, position, _ ->
            mStartActivity(FriendSettingActivity::class.java)
        }
        search_input.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                queryName = s.toString()
                fetchPageData()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })
    }
}
