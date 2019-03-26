package pro.haichuang.learn.home.ui.activity.mine

import android.app.Activity
import android.content.Intent
import android.support.design.widget.TabLayout
import com.jacy.kit.adapter.CommonAdapter
import com.jacy.kit.config.ContentView
import com.jacy.kit.config.mStartActivity
import com.jacy.kit.config.mStartActivityForResult
import com.jacy.kit.config.toast
import com.zhouyou.http.model.HttpParams
import kotlinx.android.synthetic.main.activity_order.*
import kotlinx.android.synthetic.main.item_order.view.*
import pro.haichuang.learn.home.R
import pro.haichuang.learn.home.config.BaseActivity
import pro.haichuang.learn.home.config.Constants.ORDER_ID
import pro.haichuang.learn.home.net.Url
import pro.haichuang.learn.home.ui.activity.mine.itemmodel.OrderModel
import pro.haichuang.learn.home.ui.dialog.NoticeDialog
import pro.haichuang.learn.home.utils.GsonUtil
import q.rorbin.badgeview.QBadgeView


@Suppress("INACCESSIBLE_TYPE")
@ContentView(R.layout.activity_order)
class OrderActivity : BaseActivity() {

    private var operationPosition = -1

    private val adapter by lazy {
        CommonAdapter<OrderModel>(layoutInflater, R.layout.item_order) { v, t, i ->
            v.operation.setOnClickListener {
                when (t.orderStatus) {
                    0 -> {
                        operationPosition = i
                        mStartActivityForResult(OrderDetailsActivity::class.java, 0x01, Pair(ORDER_ID, t.id))
                    }
                    1 -> NoticeDialog(this) {
                        val params = HttpParams()
                        params.put("orderId", t.id.toString())
                        post(Url.Order.Refund, params, needSession = true) {
                            t.orderStatus = 4
                            toast("退款申请成功")
                        }
                    }.show("确定退款此订单？", "款项将在1-7个工作日按照支付路径原路返回至用户的支付账户")
                    2, 3 -> mStartActivity(OrderCommentActivity::class.java)
                }
            }
        }
    }
    private var orderStatus = ""

    override fun initData() {
        titleModel.title = "我的订单"
        QBadgeView(this).bindTarget(tab.getTabAt(3)?.view).setGravityOffset(8f, true).badgeNumber = -1
        listView.adapter = adapter
        pageUrl = Url.Order.MemberList
        fetchPageData()
    }

    override fun setPageParams(pageParams: HttpParams) {
        pageParams.put("orderStatus", orderStatus)
    }

    override fun onSuccess(url: String, result: Any?) {
        when (url) {
            Url.Order.MemberList -> {
                GsonUtil.parseRows(result, OrderModel::class.java).list?.let {
                    dealRows(adapter, it)
                }
            }
        }
    }

    override fun initListener() {
        listView.setOnItemClickListener { _, _, position, _ ->
            operationPosition = position
            mStartActivityForResult(OrderDetailsActivity::class.java, 0x01, Pair(ORDER_ID, adapter.getItem(position).id))
        }
        tab.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(p0: TabLayout.Tab?) {
            }

            override fun onTabUnselected(p0: TabLayout.Tab?) {
            }

            override fun onTabSelected(p0: TabLayout.Tab?) {
                orderStatus = when (p0?.position) {
                    0 -> ""
                    else -> p0?.position?.minus(1).toString()
                }
                fetchPageData(false)
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (resultCode) {
            Activity.RESULT_OK -> adapter.getItem(operationPosition).orderStatus = 1
            Activity.RESULT_CANCELED -> adapter.getItem(operationPosition).orderStatus = -1
        }
    }
}
