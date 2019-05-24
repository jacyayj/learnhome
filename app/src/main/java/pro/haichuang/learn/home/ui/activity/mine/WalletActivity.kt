package pro.haichuang.learn.home.ui.activity.mine

import android.app.Activity
import android.content.Intent
import com.jacy.kit.adapter.CommonAdapter
import com.jacy.kit.config.ContentView
import com.jacy.kit.config.mStartActivity
import com.jacy.kit.config.mStartActivityForResult
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

    private var amount = "10"

    private val amountList by lazy { arrayListOf(10f, 50f, 100f, 500f) }

    override fun initData() {
        grid.adapter = CommonAdapter(layoutInflater, R.layout.item_wallet, amountList)
        if (SPUtils.isTeacher)
            to_tixian.show()
        post(Url.User.Account, needSession = true)
    }

    override fun onSuccess(url: String, result: Any?) {
        credit_tv.text = result?.let { GsonUtil.getString(result, "credit") } ?: "0"
        grid.setItemChecked(0, true)
    }

    override fun initListener() {
        to_payment.setOnClickListener { mStartActivityForResult(PaymentActivity::class.java, 0x01, Pair("isRecharge", true), Pair(PRICE, amount)) }
        to_pay_details.setOnClickListener { mStartActivity(PayDetailsActivity::class.java) }
        to_tixian.setOnClickListener { mStartActivityForResult(TiXianActivity::class.java, 0x02, Pair("balance", credit_tv.text.toString())) }
        grid.setOnItemClickListener { _, _, position, _ ->
            amount = amountList[position].toString()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                0x01 -> setResult(Activity.RESULT_OK, Intent().putExtra("amount", amount))
            }
            post(Url.User.Account, needSession = true)
        }
    }
}
