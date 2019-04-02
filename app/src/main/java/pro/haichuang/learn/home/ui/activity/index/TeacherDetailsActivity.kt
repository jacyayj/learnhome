package pro.haichuang.learn.home.ui.activity.index

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.Message
import com.google.gson.JsonObject
import com.jacy.kit.adapter.CommonAdapter
import com.jacy.kit.adapter.CommonRecyclerAdapter
import com.jacy.kit.config.ContentView
import com.jacy.kit.config.mStartActivity
import com.jacy.kit.config.toast
import com.netease.nim.uikit.api.NimUIKit
import com.netease.nimlib.sdk.NIMClient
import com.netease.nimlib.sdk.RequestCallback
import com.netease.nimlib.sdk.friend.FriendService
import com.netease.nimlib.sdk.friend.constant.FriendFieldEnum
import com.netease.nimlib.sdk.friend.constant.VerifyType
import com.netease.nimlib.sdk.friend.model.AddFriendData
import com.tencent.mm.opensdk.modelpay.PayReq
import com.zhouyou.http.model.HttpParams
import kotlinx.android.synthetic.main.activity_teacher_details.*
import kotlinx.android.synthetic.main.item_find_details_comment.view.*
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
import pro.haichuang.learn.home.ui.activity.find.PersonalIndexActivity
import pro.haichuang.learn.home.ui.activity.find.YuYueActivity
import pro.haichuang.learn.home.ui.activity.find.itemmodel.CommentModel
import pro.haichuang.learn.home.ui.activity.index.viewmodel.TeacherDetailsModel
import pro.haichuang.learn.home.ui.dialog.NoticeDialog
import pro.haichuang.learn.home.ui.dialog.PaymentDialog
import pro.haichuang.learn.home.utils.GsonUtil
import pro.haichuang.learn.home.utils.ShareUtils


@ContentView(R.layout.activity_teacher_details)
class TeacherDetailsActivity : DataBindingActivity<TeacherDetailsModel>() {
    private val friendSerivice by lazy { NIMClient.getService(FriendService::class.java) }
    private val id by lazy { intent.getIntExtra(TEACHER_ID, -1) }

    private var account: String = ""

    private val successDialog by lazy {
        NoticeDialog(this) {
//            toChat(-1)
        }
    }

    private val failedDialog by lazy {
        NoticeDialog(this)
    }

    private val payDialog by lazy {
        PaymentDialog(this) {
            model.payType = it
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
                            "9000" -> successDialog.show("友情提示", "支付成功！点击“咨询”可咨询老师", "咨询")

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
        comment.adapter = CommonAdapter(layoutInflater, R.layout.item_find_details_comment, arrayListOf(CommentModel(), CommentModel(), CommentModel(), CommentModel(), CommentModel(), CommentModel(), CommentModel())) { v, _, _ ->
            v.to_index.setOnClickListener {
                mStartActivity(PersonalIndexActivity::class.java)
            }
            v.comment_child.adapter = CommonRecyclerAdapter(layoutInflater,
                    R.layout.item_find_details_comment_child,
                    arrayListOf(CommentModel(), CommentModel(), CommentModel(), CommentModel()))
        }

        val params = HttpParams()
        params.put("id", id.toString())
        post(Url.Teacher.Get, params)
    }

    override fun onSuccess(url: String, result: Any?) {
        when (url) {
            Url.Teacher.Fee -> {
                payDialog.show(GsonUtil.getString(result, "teacherOnline"))
            }
            Url.Teacher.Order -> {
                when (model.payType) {
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
            Url.Teacher.Get -> {
                val model = GsonUtil.parseObject(result, TeacherDetailsModel::class.java)
                model.online = this.model.online
                notifyModel(model)
                root.scrollTo(0, 0)
            }
        }
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
        online.setOnClickListener {
            model.orderType = 1
            post(Url.Teacher.Fee)
        }
    }

    private fun toChat(id: Int) {
        if (friendSerivice.isMyFriend(account))
            friendSerivice.updateFriendFields(account, mapOf(Pair(FriendFieldEnum.EXTENSION, mapOf(Pair("orderId", id))))).setCallback(object : RequestCallback<Void> {
                override fun onSuccess(p0: Void?) {
                    NimUIKit.startP2PSession(this@TeacherDetailsActivity, account)
                }

                override fun onFailed(p0: Int) {
                }

                override fun onException(p0: Throwable?) {
                }
            })
        else friendSerivice.addFriend(AddFriendData(account, VerifyType.DIRECT_ADD)).setCallback(object : RequestCallback<Void> {
            override fun onSuccess(p0: Void?) {
                friendSerivice.updateFriendFields(account, mapOf(Pair(FriendFieldEnum.EXTENSION, mapOf(Pair("orderId", id))))).setCallback(object : RequestCallback<Void> {
                    override fun onSuccess(p0: Void?) {
                        NimUIKit.startP2PSession(this@TeacherDetailsActivity, account)
                    }

                    override fun onFailed(p0: Int) {
                    }

                    override fun onException(p0: Throwable?) {
                    }
                })

            }

            override fun onFailed(p0: Int) {

            }

            override fun onException(p0: Throwable?) {
            }
        })
    }

    inner class PayResult : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            when (intent?.getIntExtra("payResult", -1)) {
                0 -> {
                    successDialog.show("友情提示", "支付成功！点击“咨询”可咨询老师", "咨询")
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
