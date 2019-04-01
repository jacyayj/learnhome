package pro.haichuang.learn.home.ui.activity.mine

import com.jacy.kit.adapter.CommonAdapter
import com.jacy.kit.config.ContentView
import com.jacy.kit.config.toast
import com.netease.nim.uikit.api.NimUIKit
import com.netease.nimlib.sdk.NIMClient
import com.netease.nimlib.sdk.RequestCallback
import com.netease.nimlib.sdk.friend.FriendService
import com.netease.nimlib.sdk.friend.constant.FriendFieldEnum
import com.netease.nimlib.sdk.friend.constant.VerifyType
import com.netease.nimlib.sdk.friend.model.AddFriendData
import com.vondear.rxtool.RxConstTool
import com.vondear.rxtool.RxTimeTool
import com.zhouyou.http.model.HttpParams
import kotlinx.android.synthetic.main.activity_order_teacher.*
import kotlinx.android.synthetic.main.item_order_teacher.view.*
import pro.haichuang.learn.home.R
import pro.haichuang.learn.home.config.BaseActivity
import pro.haichuang.learn.home.net.Url
import pro.haichuang.learn.home.ui.activity.mine.itemmodel.OrderModel
import pro.haichuang.learn.home.utils.GsonUtil

@ContentView(R.layout.activity_order_teacher)
class OrderTeacherActivity : BaseActivity() {

    private val friendSerivice by lazy { NIMClient.getService(FriendService::class.java) }

    private val adapter by lazy {
        CommonAdapter<OrderModel>(layoutInflater, R.layout.item_order_teacher) { v, t, _ ->
            v.operation.setOnClickListener {
                when (t.orderStatus) {
                    1 -> {
                        if (t.orderType == 1) toChat(t) else
                            post(Url.Order.Finish, HttpParams().apply {
                                put("orderId", t.id.toString())
                            }) {
                                t.orderStatus = 3
                            }
                    }
                    2 -> {
                        if (RxTimeTool.getIntervalByNow("", RxConstTool.TimeUnit.HOUR) >= 24) {
                            post(Url.Order.Finish, HttpParams().apply {
                                put("orderId", t.id.toString())
                            }) {
                                t.orderStatus = 3
                            }
                        } else toChat(t)
                    }
                }
            }
        }
    }

    private fun toChat(orderModel: OrderModel) {

        orderModel.memberInfo?.let {
            friendSerivice.deleteFriend(it.imAccid)
            if (friendSerivice.isMyFriend(it.imAccid))
                if (RxTimeTool.getIntervalByNow("2019-03-30 00:00:00", RxConstTool.TimeUnit.HOUR) < 24)
                    NimUIKit.startP2PSession(this, it.imAccid)
                else toast("咨询已过期")
            else
                friendSerivice.addFriend(AddFriendData(it.imAccid, VerifyType.DIRECT_ADD)).setCallback(object : RequestCallback<Void> {
                    override fun onSuccess(p0: Void?) {
                        friendSerivice.updateFriendFields(it.imAccid, mapOf(Pair(FriendFieldEnum.EXTENSION, mapOf(Pair("orderTime", ""), Pair("orderId", orderModel.id))))).setCallback(object : RequestCallback<Void> {
                            override fun onSuccess(p0: Void?) {
                                runOnUiThread {
                                    NimUIKit.startP2PSession(this@OrderTeacherActivity, it.imAccid)
                                }
                            }

                            override fun onFailed(p0: Int) {
                            }

                            override fun onException(p0: Throwable?) {
                            }
                        })

                    }

                    override fun onFailed(p0: Int) {

                    }

                    override fun onException(p0: Throwable?) {
                    }
                })
        }

    }

    override fun initData() {
        titleModel.title = "我的订单"
        listView.adapter = adapter
        pageUrl = Url.Order.TeacherList
        fetchPageData()
    }

    override fun onSuccess(url: String, result: Any?) {
        when (url) {
            Url.Order.TeacherList -> {
                GsonUtil.parseRows(result, OrderModel::class.java).list?.let {
                    dealRows(adapter, it)
                }
            }
        }
    }
}
