package pro.haichuang.learn.home.ui.activity.mine

import com.jacy.kit.adapter.CommonAdapter
import com.jacy.kit.config.ContentView
import com.jacy.kit.config.mStartActivity
import com.jacy.kit.config.show
import kotlinx.android.synthetic.main.activity_wallet.*
import pro.haichuang.learn.home.R
import pro.haichuang.learn.home.config.BaseActivity
import pro.haichuang.learn.home.config.Constants.PRICE
import pro.haichuang.learn.home.net.Url
import pro.haichuang.learn.home.utils.GsonUtil
import pro.haichuang.learn.home.utils.SPUtils


@ContentView(R.layout.activity_wallet)
class WalletActivity : BaseActivity() {

    override fun initData() {
        grid.adapter = CommonAdapter(layoutInflater, R.layout.item_wallet, arrayListOf(10f, 50f, 100f, 500f))
        if (SPUtils.isTeacher)
            to_tixian.show()
        post(Url.User.Account, needSession = true)
    }

    override fun onSuccess(url: String, result: Any?) {
        credit_tv.text = GsonUtil.getString(result, "credit")
    }

    override fun initListener() {
        to_payment.setOnClickListener { mStartActivity(PaymentActivity::class.java, Pair("isRecharge", true), Pair(PRICE, "0.01")) }
        to_pay_details.setOnClickListener { mStartActivity(PayDetailsActivity::class.java) }
        to_tixian.setOnClickListener { mStartActivity(TiXianActivity::class.java) }
    }
}
