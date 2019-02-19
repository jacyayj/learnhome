package pro.haichuang.learn.home.ui.activity.mine

import com.jacy.kit.adapter.CommonAdapter
import com.jacy.kit.config.mStartActivity
import kotlinx.android.synthetic.main.activity_wallet.*
import pro.haichuang.learn.home.R
import com.jacy.kit.config.ContentView
import pro.haichuang.learn.home.config.BaseActivity


@ContentView(R.layout.activity_wallet)
class WalletActivity : BaseActivity() {

    override fun initData() {
        grid.adapter = CommonAdapter(layoutInflater,R.layout.item_wallet, arrayListOf(10f,50f,100f,500f))
    }

    override fun initListener() {
        to_payment.setOnClickListener { mStartActivity(PaymentActivity::class.java) }
        to_pay_details.setOnClickListener { mStartActivity(PayDetailsActivity::class.java) }
        to_tixian.setOnClickListener { mStartActivity(TiXianActivity::class.java) }
    }
}
