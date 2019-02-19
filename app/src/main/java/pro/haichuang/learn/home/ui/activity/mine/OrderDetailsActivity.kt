package pro.haichuang.learn.home.ui.activity.mine

import com.jacy.kit.adapter.CommonAdapter
import kotlinx.android.synthetic.main.activity_order_details.*
import pro.haichuang.learn.home.R
import com.jacy.kit.config.ContentView
import pro.haichuang.learn.home.bean.ImageBean
import pro.haichuang.learn.home.config.DataBindingActivity
import pro.haichuang.learn.home.ui.activity.mine.viewmodel.OrderDetailsModel


@ContentView(R.layout.activity_order_details)
class OrderDetailsActivity : DataBindingActivity<OrderDetailsModel>() {


    override fun initData() {
        model.type = intent.getIntExtra("type",1)
        titleModel.title = "订单详情"
        titleModel.showBottomeLine = false
        grid.adapter = CommonAdapter(layoutInflater, R.layout.item_square_image, arrayListOf(ImageBean(), ImageBean()))
    }
}
