package pro.haichuang.learn.home.ui.activity.mine

import android.annotation.SuppressLint
import android.os.Handler
import android.os.Message
import com.google.gson.JsonObject
import com.jacy.kit.config.ContentView
import com.jacy.kit.config.mStartActivity
import com.jacy.kit.config.toast
import com.tencent.mm.opensdk.modelpay.PayReq
import kotlinx.android.synthetic.main.activity_payment.*
import pro.haichuang.learn.home.R
import pro.haichuang.learn.home.config.Constants
import pro.haichuang.learn.home.config.Constants.PRICE
import pro.haichuang.learn.home.config.DataBindingActivity
import pro.haichuang.learn.home.net.Url
import pro.haichuang.learn.home.ui.activity.mine.viewmodel.PaymentModel
import pro.haichuang.learn.home.utils.GsonUtil
import pro.haichuang.learn.home.utils.ShareUtils


@ContentView(R.layout.activity_payment)
class PaymentActivity : DataBindingActivity<PaymentModel>() {

    private val mHandler by lazy {
        @SuppressLint("HandlerLeak")
        object : Handler() {
            override fun handleMessage(msg: Message?) {
                when (msg?.what) {
                    Constants.ALIPAY -> {
                        when (GsonUtil.getString(msg.obj, "resultStatus")) {
                            "9000" -> {
                                toast("支付成功")
                                finish()
                            }
                            "6001" -> toast("支付取消")
                        }
                    }
                }
            }
        }
    }

    override fun initData() {
        model.price = intent.getStringExtra(PRICE)
        model.recharge = intent.getBooleanExtra("isRecharge", false)
    }

    override fun onSuccess(url: String, result: Any?) {
        when (url) {
            Url.Account.Activate,Url.Account.Recharge -> {
                when (model.type) {
                    1 -> {
                        toast("钱包支付未完成")
                    }
                    12 -> {
                        val mreq = GsonUtil.parseObject(result, JsonObject::class.java)
                        val req = PayReq()
                        req.appId = mreq.get("appid").asString
                        req.partnerId = mreq.get("partnerid").asString
                        req.prepayId = mreq.get("prepayid").asString
                        req.packageValue = mreq.get("package").asString
                        req.nonceStr = mreq.get("noncestr").asString
                        req.timeStamp = mreq.get("timestamp").asLong.toString()
                        req.sign = mreq.get("sign").asString
                        ShareUtils.toWXPay(req)
                    }
                    13 -> {
                        ShareUtils.toAliPay(GsonUtil.getString(result, "orderString"), this, mHandler)
                    }
                }
            }
        }
    }

    override fun initListener() {
        to_payment.setOnClickListener {
            mStartActivity(WalletActivity::class.java)
        }
        pay.setOnClickListener {
            if (model.recharge)
                autoPost(Url.Account.Recharge, showLoading = true, needSession = true)
            else
                autoPost(Url.Account.Activate, showLoading = true, needSession = true)
        }
    }
}
