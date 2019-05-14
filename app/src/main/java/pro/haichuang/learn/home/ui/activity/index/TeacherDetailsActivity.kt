package pro.haichuang.learn.home.ui.activity.index

import android.annotation.SuppressLint
import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.Message
import com.google.gson.JsonObject
import com.jacy.kit.adapter.CommonAdapter
import com.jacy.kit.config.ContentView
import com.jacy.kit.config.mStartActivity
import com.jacy.kit.config.toast
import com.tencent.mm.opensdk.modelpay.PayReq
import com.zhouyou.http.model.HttpParams
import kotlinx.android.synthetic.main.activity_teacher_details.*
import pro.haichuang.learn.home.R
import pro.haichuang.learn.home.config.Constants
import pro.haichuang.learn.home.config.Constants.TEACHER_ACCOUNT
import pro.haichuang.learn.home.config.Constants.TEACHER_HEADER
import pro.haichuang.learn.home.config.Constants.TEACHER_ID
import pro.haichuang.learn.home.config.Constants.TEACHER_NAME
import pro.haichuang.learn.home.config.Constants.TEACHER_SKILL
import pro.haichuang.learn.home.config.Constants.TEACHER_SUBJECT
import pro.haichuang.learn.home.config.Constants.TEACHER_TYPE
import pro.haichuang.learn.home.config.DataBindingActivity
import pro.haichuang.learn.home.net.Url
import pro.haichuang.learn.home.ui.activity.find.YuYueActivity
import pro.haichuang.learn.home.ui.activity.find.itemmodel.CommentModel
import pro.haichuang.learn.home.ui.activity.index.viewmodel.TeacherDetailsModel
import pro.haichuang.learn.home.ui.activity.mine.SettPwdActivity
import pro.haichuang.learn.home.ui.dialog.NoticeDialog
import pro.haichuang.learn.home.ui.dialog.PasswordDialog
import pro.haichuang.learn.home.ui.dialog.PaymentDialog
import pro.haichuang.learn.home.utils.GsonUtil
import pro.haichuang.learn.home.utils.SPUtils
import pro.haichuang.learn.home.utils.ShareUtils


@ContentView(R.layout.activity_teacher_details)
class TeacherDetailsActivity : DataBindingActivity<TeacherDetailsModel>() {

    private val id by lazy { intent.getIntExtra(TEACHER_ID, -1) }

    private var account: String = ""
    private val adapter by lazy {
        CommonAdapter<CommentModel>(layoutInflater, R.layout.item_find_details_comment) { _, t, _ ->
            t.teacher = true
        }
    }
    private val successDialog by lazy {
        NoticeDialog(this)
    }

    private val payDialog by lazy {
        PaymentDialog(this) {
            model.payType = it
            if (it == 1) {
                if (SPUtils.hasPayPassword)
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

    override fun initData() {
        model.online = intent.getBooleanExtra("online", false)
        titleModel.title = if (model.online) "名师在线" else "老师详情"
        comment.adapter = adapter
        post(Url.Teacher.Get, HttpParams("id", id.toString()))
        post(Url.Teacher.CommentList, HttpParams("teacherId", id.toString()))
    }

    override fun onSuccess(url: String, result: Any?) {
        when (url) {
            Url.User.Account -> {
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
            Url.Teacher.Get -> {
                val model = GsonUtil.parseObject(result, TeacherDetailsModel::class.java)
                model.online = this.model.online
                notifyModel(model)
            }

            Url.Teacher.CommentList -> {
                GsonUtil.parseRows(result, CommentModel::class.java).list?.let {
                    dealRows(adapter, it)
                }
            }
        }
    }

    fun showSuccess() {
        successDialog.show("友情提示", "支付成功！请等待老师接单", "确定")
    }

    override fun initListener() {
        to_yuyue.setOnClickListener {
            mStartActivity(YuYueActivity::class.java,
                    Pair(TEACHER_ID, model.id),
                    Pair(TEACHER_HEADER, model.teacherImg),
                    Pair(TEACHER_SUBJECT, model.subject),
                    Pair(TEACHER_TYPE, model.type),
                    Pair(TEACHER_NAME, model.teachername),
                    Pair(TEACHER_ACCOUNT, account),
                    Pair(TEACHER_SKILL, model.skill))
        }
        off_online.setOnClickListener {
            mStartActivity(YuYueActivity::class.java,
                    Pair(TEACHER_ID, model.id),
                    Pair(TEACHER_HEADER, model.teacherImg),
                    Pair(TEACHER_SUBJECT, model.subject),
                    Pair(TEACHER_TYPE, model.type),
                    Pair(TEACHER_NAME, model.teachername),
                    Pair(TEACHER_ACCOUNT, account),
                    Pair(TEACHER_SKILL, model.skill))
        }
        follow.setOnClickListener {
            post(Url.Teacher.Collect, HttpParams("teacherId", model.id.toString()).apply {
                put("operate", if (model.hasCollect) "1" else "0")
            }, needSession = true) {
                toast(if (model.hasCollect) "取消关注成功" else "关注成功")
                model.hasCollect = model.hasCollect.not()
            }
        }
        online.setOnClickListener {
            model.orderType = 1
            post(Url.Teacher.Fee)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            data?.let {
                payDialog.refrshBalance(it.getStringExtra("amount"))
            }
        }
    }

    inner class PayResult : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            when (intent?.getIntExtra("payResult", -1)) {
                0 -> showSuccess()
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
