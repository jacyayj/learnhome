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
import com.jacy.kit.config.toast
import com.tencent.mm.opensdk.modelpay.PayReq
import kotlinx.android.synthetic.main.activity_order_details.*
import pro.haichuang.learn.home.R
import pro.haichuang.learn.home.config.Constants
import pro.haichuang.learn.home.config.Constants.ORDER_ID
import pro.haichuang.learn.home.config.Constants.TEACHER_ID
import pro.haichuang.learn.home.config.DataBindingActivity
import pro.haichuang.learn.home.net.Url
import pro.haichuang.learn.home.ui.activity.mine.viewmodel.OrderDetailsModel
import pro.haichuang.learn.home.utils.GsonUtil
import pro.haichuang.learn.home.utils.ShareUtils


@ContentView(R.layout.activity_order_details)
class OrderDetailsActivity : DataBindingActivity<OrderDetailsModel>() {

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
                            "6001" -> {
                                autoPost(Url.Order.Get, showLoading = false, needSession = true)
                                toast("支付取消")
                            }
                        }
                    }
                }
            }
        }
    }
    private val receiver by lazy { PayResult() }

    override fun initData() {
        registerReceiver(receiver, IntentFilter("payResult"))
        model.id = intent.getIntExtra(ORDER_ID, -1)
        titleModel.title = "订单详情"
        titleModel.showBottomeLine = false
        autoPost(Url.Order.Get, needSession = true)
    }

    override fun initListener() {
        pay.setOnClickListener {
            autoPost(Url.Order.Pay, needSession = true)
        }
        cancel.setOnClickListener {
            autoPost(Url.Order.Cancel, needSession = true)
        }
        comment.setOnClickListener {
            mStartActivity(OrderCommentActivity::class.java, Pair(TEACHER_ID, model.teacherInfo?.id))
        }
    }

    override fun onSuccess(url: String, result: Any?) {
        when (url) {
            Url.Order.Pay -> {
                when (model.payType) {
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
                    13 -> ShareUtils.toAliPay(GsonUtil.getString(result, "orderString"), this, mHandler)
                }
            }
            Url.Order.Cancel -> {
                toast("取消成功")
                setResult(Activity.RESULT_CANCELED)
                finish()
            }
            Url.Order.Get -> notifyModel(GsonUtil.parseObject(result, OrderDetailsModel::class.java))
        }
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
                    autoPost(Url.Order.Get, showLoading = false, needSession = true)
                    toast("支付取消")
                }
                2 -> {
                    autoPost(Url.Order.Get, showLoading = false, needSession = true)
                    toast("支付失败")
                }
            }
        }
    }
}
