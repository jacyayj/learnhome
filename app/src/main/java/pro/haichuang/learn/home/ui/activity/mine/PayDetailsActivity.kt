package pro.haichuang.learn.home.ui.activity.mine

import com.jacy.kit.adapter.CommonAdapter
import com.jacy.kit.config.ContentView
import kotlinx.android.synthetic.main.activity_pay_details.*
import pro.haichuang.learn.home.R
import pro.haichuang.learn.home.config.BaseActivity
import pro.haichuang.learn.home.net.Url
import pro.haichuang.learn.home.ui.activity.mine.itemmodel.PayModel
import pro.haichuang.learn.home.utils.GsonUtil


@ContentView(R.layout.activity_pay_details)
class PayDetailsActivity : BaseActivity() {

    override fun initData() {
        titleModel.title = "明细"
        post(Url.Account.Detail,needSession = true)
    }

    override fun onSuccess(url: String, result: Any?) {
        listView.adapter = CommonAdapter(layoutInflater,R.layout.item_pay_details,GsonUtil.parseArray(result,PayModel::class.java))
    }
}
