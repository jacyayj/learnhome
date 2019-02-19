package pro.haichuang.learn.home.ui.activity.mine

import com.jacy.kit.config.mStartActivity
import kotlinx.android.synthetic.main.activity_payment.*
import pro.haichuang.learn.home.R
import com.jacy.kit.config.ContentView
import pro.haichuang.learn.home.config.DataBindingActivity
import pro.haichuang.learn.home.ui.activity.mine.viewmodel.PaymentModel


@ContentView(R.layout.activity_payment)
class PaymentActivity : DataBindingActivity<PaymentModel>() {

    override fun initData() {
    }

    override fun initListener() {
        to_payment.setOnClickListener {
            mStartActivity(WalletActivity::class.java)
        }
    }
}
