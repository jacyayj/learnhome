package pro.haichuang.learn.home.ui.activity.mine

import android.annotation.SuppressLint
import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Handler
import android.os.Message
import com.google.gson.JsonObject
import com.jacy.kit.config.ContentView
import com.jacy.kit.config.mStartActivity
import com.jacy.kit.config.mStartActivityForResult
import com.jacy.kit.config.toast
import com.tencent.mm.opensdk.modelpay.PayReq
import kotlinx.android.synthetic.main.activity_payment.*
import pro.haichuang.learn.home.R
import pro.haichuang.learn.home.config.Constants
import pro.haichuang.learn.home.config.Constants.PRICE
import pro.haichuang.learn.home.config.DataBindingActivity
import pro.haichuang.learn.home.net.Url
import pro.haichuang.learn.home.ui.activity.mine.viewmodel.PaymentModel
import pro.haichuang.learn.home.ui.dialog.PasswordDialog
import pro.haichuang.learn.home.utils.GsonUtil
import pro.haichuang.learn.home.utils.SPUtils
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
                                setResult(Activity.RESULT_OK)
                                finish()
                            }
                            "6001" -> toast("支付取消")
                        }
                    }
                }
            }
        }
    }
    private val receiver by lazy { PayResult() }
    override fun initData() {
        registerReceiver(receiver, IntentFilter("payResult"))
        model.price = intent.getStringExtra(PRICE)
        model.recharge = intent.getBooleanExtra("isRecharge", false)
        if (model.recharge.not())
            post(Url.User.Account, needSession = true)
    }

    @SuppressLint("SetTextI18n")
    override fun onSuccess(url: String, result: Any?) {
        when (url) {
            Url.User.Account -> {
                balance.text = "（账户余额:￥${GsonUtil.getString(result, "credit")}"
            }
            Url.Account.Activate, Url.Account.Recharge -> {
                when (model.type) {
                    1 -> {
                        toast("支付成功")
                        setResult(Activity.RESULT_OK)
                        finish()
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
            mStartActivityForResult(WalletActivity::class.java, 0x01)
        }
        pay.setOnClickListener {
            if (model.recharge)
                autoPost(Url.Account.Recharge, showLoading = true, needSession = true)
            else {
                if (model.type == 1)
                    if (SPUtils.hasPayPassword)
                        PasswordDialog(this) {
                            model.payPassword = it
                            autoPost(Url.Account.Activate, showLoading = true, needSession = true)
                        }.show()
                    else
                        mStartActivity(SettPwdActivity::class.java)
                else
                    autoPost(Url.Account.Activate, showLoading = true, needSession = true)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (Activity.RESULT_OK == resultCode)
            post(Url.User.Account, needSession = true)
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(receiver)
    }

    inner class PayResult : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            when (intent?.getIntExtra("payResult", -1)) {
                0 -> {
                    toast("支付成功")
                    setResult(Activity.RESULT_OK)
                    finish()
                }
                1 -> {
                    toast("支付取消")
                }
                2 -> {
                    toast("支付失败")
                }
            }
        }
    }
}
