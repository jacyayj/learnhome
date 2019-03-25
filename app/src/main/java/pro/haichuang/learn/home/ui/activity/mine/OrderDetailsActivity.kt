package pro.haichuang.learn.home.ui.activity.mine

import com.jacy.kit.config.ContentView
import pro.haichuang.learn.home.R
import pro.haichuang.learn.home.config.Constants.ORDER_NUMBER
import pro.haichuang.learn.home.config.DataBindingActivity
import pro.haichuang.learn.home.net.Url
import pro.haichuang.learn.home.ui.activity.mine.viewmodel.OrderDetailsModel
import pro.haichuang.learn.home.utils.GsonUtil


@ContentView(R.layout.activity_order_details)
class OrderDetailsActivity : DataBindingActivity<OrderDetailsModel>() {


    override fun initData() {
        model.orderNumber = intent.getStringExtra(ORDER_NUMBER)
        titleModel.title = "订单详情"
        titleModel.showBottomeLine = false
        autoPost(Url.Order.Get, needSession = true)
//        grid.adapter = CommonAdapter(layoutInflater, R.layout.item_square_image, arrayListOf(ImageBean(), ImageBean()))
    }


    override fun onSuccess(url: String, result: Any?) {
        when (url) {
            Url.Order.Get -> {
                notifyModel(GsonUtil.parseObject(result, OrderDetailsModel::class.java))
            }
        }
    }
}
