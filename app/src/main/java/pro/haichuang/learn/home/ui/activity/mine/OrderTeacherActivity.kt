package pro.haichuang.learn.home.ui.activity.mine

import com.jacy.kit.adapter.CommonAdapter
import com.jacy.kit.config.ContentView
import com.jacy.kit.config.toast
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
import pro.haichuang.learn.home.utils.HttpUtils

@ContentView(R.layout.activity_order_teacher)
class OrderTeacherActivity : BaseActivity() {

    private var tempItem: OrderModel? = null

    private val adapter by lazy {
        CommonAdapter<OrderModel>(layoutInflater, R.layout.item_order_teacher) { v, t, _ ->
            v.operation.setOnClickListener {
                if (t.acceptTime.isEmpty()) {
                    tempItem = t
                    post(Url.Order.Get, HttpParams("orderId", t.id.toString()), needSession = true)
                } else
                    deal(t)
            }
        }
    }

    private fun deal(t: OrderModel) {
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
                if (RxTimeTool.getIntervalByNow(t.acceptTime, RxConstTool.TimeUnit.HOUR) >= 24) {
                    post(Url.Order.Finish, HttpParams("orderId", t.id.toString()), needSession = true) {
                        t.orderStatus = 3
                        toast("该咨询已过期")
                    }
                } else toChat(t)
            }
        }
    }

    private fun toChat(orderModel: OrderModel) {
        orderModel.memberInfo?.let {
            HttpUtils.updateOrderTime(this, it.imAccid, orderModel.acceptTime, orderModel.id, true)
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
            Url.Order.Get -> {
                tempItem?.let {
                    it.acceptTime = GsonUtil.getString(result, "acceptTime")
                    deal(it)
                }
            }
            Url.Order.TeacherList -> {
                GsonUtil.parseRows(result, OrderModel::class.java).list?.let {
                    dealRows(adapter, it)
                }
            }
        }
    }
}
