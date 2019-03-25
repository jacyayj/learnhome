package pro.haichuang.learn.home.ui.activity.mine

import android.support.design.widget.TabLayout
import com.jacy.kit.adapter.CommonAdapter
import com.jacy.kit.config.ContentView
import com.jacy.kit.config.mStartActivity
import com.jacy.kit.config.toast
import com.zhouyou.http.model.HttpParams
import kotlinx.android.synthetic.main.activity_order.*
import kotlinx.android.synthetic.main.item_order.view.*
import pro.haichuang.learn.home.R
import pro.haichuang.learn.home.config.BaseActivity
import pro.haichuang.learn.home.config.Constants.ORDER_NUMBER
import pro.haichuang.learn.home.net.Url
import pro.haichuang.learn.home.ui.activity.mine.itemmodel.OrderModel
import pro.haichuang.learn.home.ui.dialog.NoticeDialog
import pro.haichuang.learn.home.utils.GsonUtil
import pro.haichuang.learn.home.utils.SPUtils
import q.rorbin.badgeview.QBadgeView


@Suppress("INACCESSIBLE_TYPE")
@ContentView(R.layout.activity_order)
class OrderActivity : BaseActivity() {

    private val noticeDialog by lazy { NoticeDialog(this) }
    private val adapter by lazy {
        CommonAdapter<OrderModel>(layoutInflater, R.layout.item_order) { v, t, _ ->
            v.operation.setOnClickListener {
                when (t.orderStatus) {
                    0 -> toast("去付款")
                    1 -> noticeDialog.show("确定退款此订单？", "款项将在1-7个工作日按照支付路径原路返回至用户的支付账户")
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
        pageUrl = if (SPUtils.isTeacher) Url.Order.TeacherList else Url.Order.MemberList
        fetchPageData()
    }

    override fun setPageParams(pageParams: HttpParams) {
        pageParams.put("orderStatus", orderStatus)
    }

    override fun onSuccess(url: String, result: Any?) {
        when (url) {
            Url.Order.MemberList, Url.Order.TeacherList -> {
                GsonUtil.parseRows(result, OrderModel::class.java).list?.let {
                    dealRows(adapter, it)
                }
            }
        }
    }

    override fun initListener() {
        listView.setOnItemClickListener { _, _, position, _ ->
            mStartActivity(OrderDetailsActivity::class.java, Pair(ORDER_NUMBER, adapter.getItem(position).orderNumber))
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

}
