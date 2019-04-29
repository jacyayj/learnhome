package pro.haichuang.learn.home.ui.activity.message

import android.text.Editable
import android.text.TextWatcher
import com.jacy.kit.adapter.CommonAdapter
import com.jacy.kit.config.mStartActivity
import kotlinx.android.synthetic.main.activity_friend_search.*
import pro.haichuang.learn.home.R
import com.jacy.kit.config.ContentView
import com.jacy.kit.config.toast
import com.netease.nimlib.sdk.NIMClient
import com.netease.nimlib.sdk.RequestCallback
import com.netease.nimlib.sdk.friend.FriendService
import com.netease.nimlib.sdk.friend.constant.VerifyType
import com.netease.nimlib.sdk.friend.model.AddFriendData
import com.netease.nimlib.service.NimService
import com.zhouyou.http.model.HttpParams
import kotlinx.android.synthetic.main.item_search_friend.view.*
import pro.haichuang.learn.home.config.BaseActivity
import pro.haichuang.learn.home.net.Url
import pro.haichuang.learn.home.ui.activity.message.itemmodel.FriendModel
import pro.haichuang.learn.home.utils.GsonUtil
import pro.haichuang.learn.home.utils.SPUtils


@ContentView(R.layout.activity_friend_search)
class FriendSearchActivity : BaseActivity() {

    private val friendService by lazy { NIMClient.getService(FriendService::class.java) }

    private val adapter by lazy {
        CommonAdapter<FriendModel>(layoutInflater, R.layout.item_search_friend) { v, t, _ ->
            v.add_friend.setOnClickListener {
                friendService.addFriend(AddFriendData(t.imAccid, VerifyType.VERIFY_REQUEST, "我是${SPUtils.userName}")).setCallback(object : RequestCallback<Void> {
                    override fun onSuccess(p0: Void?) {
                        toast("好友请求发送成功")
                    }

                    override fun onFailed(p0: Int) {
                    }

                    override fun onException(p0: Throwable?) {
                    }
                })
            }
        }
    }

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
