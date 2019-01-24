package pro.haichuang.learn.home.ui.activity.mine

import android.support.design.widget.TabLayout
import com.jacy.kit.adapter.CommonAdapter
import com.jacy.kit.config.mStartActivity
import kotlinx.android.synthetic.main.activity_order.*
import kotlinx.android.synthetic.main.item_order.view.*
import pro.haichuang.learn.home.R
import pro.haichuang.learn.home.annotation.ContentView
import pro.haichuang.learn.home.config.BaseActivity
import pro.haichuang.learn.home.ui.activity.mine.itemmodel.OrderModel
import pro.haichuang.learn.home.ui.dialog.NoticeDialog
import q.rorbin.badgeview.QBadgeView


@Suppress("INACCESSIBLE_TYPE")
@ContentView(R.layout.activity_order)
class OrderActivity : BaseActivity() {

    private val noticeDialog by lazy { NoticeDialog(this) }
    private val data by lazy { arrayListOf(OrderModel(1), OrderModel(1), OrderModel(2), OrderModel(3)) }

    override fun initData() {
        titleModel.title = "我的订单"
        QBadgeView(this).bindTarget(tab.getTabAt(3)?.view).setGravityOffset(8f, true).badgeNumber = -1
        listView.adapter = CommonAdapter(layoutInflater, R.layout.item_order, data) { v, t, _ ->
            v.operation.setOnClickListener {
                when (t.type) {
                    1 -> mStartActivity(OrderDetailsActivity::class.java, Pair("type", t.type))
                    2 -> noticeDialog.show("确定退款此订单？", "款项将在1-7个工作日按照支付路径原路返回至用户的支付账户")
                    3 -> mStartActivity(OrderCommentActivity::class.java)
                }
            }
        }
    }

    override fun initListener() {
        listView.setOnItemClickListener { _, _, position, _ ->
            mStartActivity(OrderDetailsActivity::class.java, Pair("type", data[position].type))
        }
        tab.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(p0: TabLayout.Tab?) {
            }

            override fun onTabUnselected(p0: TabLayout.Tab?) {
            }

            override fun onTabSelected(p0: TabLayout.Tab?) {
                data.forEach {
                    it.type = p0?.position ?: 0
                }
            }
        })
    }

}
