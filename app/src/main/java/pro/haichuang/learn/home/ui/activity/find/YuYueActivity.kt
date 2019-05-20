package pro.haichuang.learn.home.ui.activity.find

import android.annotation.SuppressLint
import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.Message
import android.widget.RadioButton
import com.google.gson.JsonObject
import com.jacy.kit.config.ContentView
import com.jacy.kit.config.mStartActivity
import com.jacy.kit.config.toast
import com.luck.picture.lib.PictureSelector
import com.luck.picture.lib.config.PictureConfig
import com.netease.nim.uikit.api.NimUIKit
import com.tencent.mm.opensdk.modelpay.PayReq
import com.zhouyou.http.model.HttpParams
import kotlinx.android.synthetic.main.activity_yu_yue.*
import pro.haichuang.learn.home.R
import pro.haichuang.learn.home.adapter.ReleaseImageAdapter
import pro.haichuang.learn.home.config.Constants
import pro.haichuang.learn.home.config.Constants.RECHARGE
import pro.haichuang.learn.home.config.Constants.TEACHER_ACCOUNT
import pro.haichuang.learn.home.config.Constants.TEACHER_HEADER
import pro.haichuang.learn.home.config.Constants.TEACHER_ID
import pro.haichuang.learn.home.config.Constants.TEACHER_NAME
import pro.haichuang.learn.home.config.Constants.TEACHER_SKILL
import pro.haichuang.learn.home.config.Constants.TEACHER_SUBJECT
import pro.haichuang.learn.home.config.Constants.TEACHER_TYPE
import pro.haichuang.learn.home.config.DataBindingActivity
import pro.haichuang.learn.home.net.Url
import pro.haichuang.learn.home.ui.activity.find.viewmodel.YuYueModel
import pro.haichuang.learn.home.ui.activity.mine.SettPwdActivity
import pro.haichuang.learn.home.ui.dialog.NoticeDialog
import pro.haichuang.learn.home.ui.dialog.PasswordDialog
import pro.haichuang.learn.home.ui.dialog.PaymentDialog
import pro.haichuang.learn.home.utils.GsonUtil
import pro.haichuang.learn.home.utils.SPUtils
import pro.haichuang.learn.home.utils.ShareUtils
import java.io.File

@ContentView(R.layout.activity_yu_yue)
class YuYueActivity : DataBindingActivity<YuYueModel>() {
    private val adapter by lazy { ReleaseImageAdapter(this) }

    private val payDialog by lazy {
        PaymentDialog(this) {
            model.payType = it
            if (it == 1) {
                if (model.hasPayPassword)
                    PasswordDialog(this) {
                        model.payPassword = it
                        autoPost(Url.Teacher.Order, needSession = true)
                    }.show()
                else
                    mStartActivity(SettPwdActivity::class.java)
            } else
                autoPost(Url.Teacher.Order, needSession = true)
        }
    }

    private val successDialog by lazy {
        NoticeDialog(this) {
            NimUIKit.startP2PSession(this, model.account)
        }
    }

    private val failedDialog by lazy {
        NoticeDialog(this)
    }

    private val mHandler by lazy {
        @SuppressLint("HandlerLeak")
        object : Handler() {
            override fun handleMessage(msg: Message?) {
                when (msg?.what) {
                    Constants.ALIPAY -> {
                        when (GsonUtil.getString(msg.obj, "resultStatus")) {
                            "9000" -> showSuccess()
                            "6001" -> toast("支付取消")
                        }
                    }
                }
            }
        }
    }

    fun showSuccess() {
        successDialog.show("友情提示", "支付成功！请等待老师接单。", "确定")
    }

    override fun initData() {
        titleModel.title = "预约填报"
        grid.adapter = adapter
        model.id = intent.getIntExtra(TEACHER_ID, -1)
        model.name = intent.getStringExtra(TEACHER_NAME)
        model.skill = intent.getStringExtra(TEACHER_SKILL)
        model.header = intent.getStringExtra(TEACHER_HEADER)
        model.account = intent.getStringExtra(TEACHER_ACCOUNT)
        model.type = intent.getIntExtra(TEACHER_TYPE, -1)
        model.subject = intent.getIntExtra(TEACHER_SUBJECT, -1)
        model.appointTime = time_1.text.toString()
    }

    override fun initListener() {
        yu_yue.setOnClickListener {
            autoPost(Url.Teacher.Fee, needSession = true)
        }
        time_group.setOnCheckedChangeListener { v, checkedId ->
            model.appointTime = v.findViewById<RadioButton>(checkedId).text.toString()
        }
    }

    override fun onSuccess(url: String, result: Any?) {
        when (url) {
            Url.User.Account -> {
                model.hasPayPassword = GsonUtil.getBoolean(result, "hasPayPassword")
                payDialog.balance = GsonUtil.getString(result, "credit")
                payDialog.show()
            }
            Url.Teacher.Fee -> {
                payDialog.price = GsonUtil.getString(result, "teacherOffline")
                post(Url.User.Account, needSession = true)
            }
            Url.Teacher.Order -> {
                when (model.payType) {
                    1 -> showSuccess()
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
            Url.Upload.Upload -> {
                adapter.insertUpload(GsonUtil.getString(result, "fileName"), GsonUtil.getString(result, "uploadPath"))
                model.picPaths = adapter.getPicPaths()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                PictureConfig.CHOOSE_REQUEST -> {
                    PictureSelector.obtainMultipleResult(data).forEach { m ->
                        val file = File(m.compressPath)
                        adapter.insert(m.compressPath, file.name)
                        val params = HttpParams()
                        params.put("mobile", SPUtils.phone)
                        params.put("type", "image")
                        params.put("uploadFile", file, null)
                        post(Url.Upload.Upload, params)
                    }
                }
                RECHARGE -> {
                    data?.let {
                        payDialog.refrshBalance(it.getStringExtra("amount"))
                    }
                }
            }
        }
    }

    inner class PayResult : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            when (intent?.getIntExtra("payResult", -1)) {
                0 -> showSuccess()
                1 -> toast("支付取消")
                2 -> toast("支付失败")
            }
        }
    }
}
