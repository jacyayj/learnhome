package pro.haichuang.learn.home.ui.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import com.jacy.kit.utils.toRoundInt
import kotlinx.android.synthetic.main.dialog_payment.*
import pro.haichuang.learn.home.R
import pro.haichuang.learn.home.utils.DialogUtils

class PaymentDialog(context: Context, private val result: (type: Int) -> Unit) : Dialog(context, R.style.Dialog), View.OnClickListener {

    private var payType = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_payment)
        setCanceledOnTouchOutside(true)
        DialogUtils.initDialogWidth(window, 0.75f)
        confirm.setOnClickListener {
            result(payType)
            dismiss()
        }
        wallet_view.setOnClickListener(this)
        wechat_pay.setOnClickListener(this)
        alipay.setOnClickListener(this)
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


    fun show(price: String) {
        super.show()
        price_tv.text = price
        total_price_tv.text = "预约价格：${price}元"
    }
}