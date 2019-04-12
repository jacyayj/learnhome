package pro.haichuang.learn.home.ui.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import com.jacy.kit.config.mStartActivityForResult
import com.jacy.kit.utils.toRoundInt
import kotlinx.android.synthetic.main.dialog_payment.*
import pro.haichuang.learn.home.R
import pro.haichuang.learn.home.config.Constants
import pro.haichuang.learn.home.ui.activity.mine.WalletActivity
import pro.haichuang.learn.home.utils.DialogUtils

class PaymentDialog(context: Context, private val result: (type: Int) -> Unit) : Dialog(context, R.style.Dialog), View.OnClickListener {

    private var payType = 1
    var price: String = ""
    var balance: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_payment)
        setCanceledOnTouchOutside(false)
        close.setOnClickListener {
            dismiss()
        }
        DialogUtils.initDialogWidth(window, 0.75f)
        confirm.setOnClickListener {
            result(payType)
            dismiss()
        }
        to_payment.setOnClickListener {
            context.mStartActivityForResult(WalletActivity::class.java, Constants.RECHARGE)
        }
        wallet_view.setOnClickListener(this)
        wechat_pay.setOnClickListener(this)
        alipay.setOnClickListener(this)
    }

    fun refrshBalance(amount: String) {
        balance = (balance.toFloatOrNull() ?: 0f + (amount.toFloatOrNull() ?: 0f)).toString()
        balance_tv.text = "（账户余额:￥$balance"
    }

    override fun onClick(v: View?) {
        when (v?.tag?.toRoundInt()) {
            0 -> {
                wallet.isChecked = true
                alipay.isChecked = false
                wechat_pay.isChecked = false
                payType = 1
            }
            1 -> {
                wallet.isChecked = false
                alipay.isChecked = false
                wechat_pay.isChecked = true
                payType = 12
            }
            2 -> {
                wallet.isChecked = false
                alipay.isChecked = true
                wechat_pay.isChecked = false
                payType = 13
            }
        }
    }

    override fun show() {
        super.show()
        price_tv.text = price
        total_price_tv.text = "预约价格：${price}元"
        balance_tv.text = "（账户余额:￥$balance"
    }
}