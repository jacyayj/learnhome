package pro.haichuang.learn.home.ui.activity.mine

import com.jacy.kit.config.ContentView
import com.jacy.kit.config.mStartActivity
import com.jacy.kit.config.toast
import kotlinx.android.synthetic.main.activity_bind_vip.*
import pro.haichuang.learn.home.R
import pro.haichuang.learn.home.config.Constants.PRICE
import pro.haichuang.learn.home.config.DataBindingActivity
import pro.haichuang.learn.home.net.Url
import pro.haichuang.learn.home.ui.activity.mine.viewmodel.BindVipModel
import pro.haichuang.learn.home.utils.GsonUtil


@ContentView(R.layout.activity_bind_vip)
class BindVipActivity : DataBindingActivity<BindVipModel>() {

    override fun initData() {
        titleModel.title = "绑定新卡"
        post(Url.Account.Fee)
    }

    override fun onSuccess(url: String, result: Any?) {
        when (url) {
            Url.Account.Fee -> model.price = GsonUtil.getString(result, "vipFee")
            Url.Account.Order -> {
                toast("激活成功")
                finish()
            }
        }
    }

    override fun initListener() {
        buy.setOnClickListener {
            mStartActivity(PaymentActivity::class.java, Pair(PRICE, model.price))
        }
        upgrade.setOnClickListener {
            autoPost(Url.Account.Order, needSession = true)
        }
    }
}
