package pro.haichuang.learn.home.ui.activity.mine

import com.jacy.kit.adapter.CommonAdapter
import kotlinx.android.synthetic.main.activity_pay_details.*
import pro.haichuang.learn.home.R
import com.jacy.kit.config.ContentView
import pro.haichuang.learn.home.config.BaseActivity


@ContentView(R.layout.activity_pay_details)
class PayDetailsActivity : BaseActivity() {

    override fun initData() {
        titleModel.title = "明细"
        listView.adapter = CommonAdapter(layoutInflater,R.layout.item_pay_details, arrayListOf(1,2,5,3,5,6))
    }

}
