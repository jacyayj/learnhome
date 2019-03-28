package pro.haichuang.learn.home.ui.activity.mine

import com.jacy.kit.adapter.CommonAdapter
import com.jacy.kit.config.ContentView
import com.netease.nim.uikit.api.NimUIKit
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
    private val adapter by lazy {
        CommonAdapter<OrderModel>(layoutInflater, R.layout.item_order_teacher) { v, t, _ ->
            v.operation.setOnClickListener {
                when (t.orderStatus) {
                    1 -> {
                        if (t.orderType == 1)
                            NimUIKit.startP2PSession(this, t.memberInfo?.imAccid)
                        else
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
                        } else
                            NimUIKit.startP2PSession(this, "")
                    }
                }
            }
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
