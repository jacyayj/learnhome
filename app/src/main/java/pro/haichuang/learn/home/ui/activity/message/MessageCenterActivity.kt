package pro.haichuang.learn.home.ui.activity.message

import com.jacy.kit.adapter.CommonAdapter
import com.jacy.kit.config.ContentView
import com.jacy.kit.config.toJson
import com.netease.nim.uikit.api.NimUIKit
import com.netease.nimlib.sdk.NIMClient
import com.netease.nimlib.sdk.RequestCallback
import com.netease.nimlib.sdk.friend.model.AddFriendNotify
import com.netease.nimlib.sdk.msg.MessageBuilder
import com.netease.nimlib.sdk.msg.MsgService
import com.netease.nimlib.sdk.msg.SystemMessageService
import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum
import com.netease.nimlib.sdk.msg.constant.SystemMessageStatus
import com.netease.nimlib.sdk.msg.constant.SystemMessageType
import com.netease.nimlib.sdk.msg.model.IMMessage
import com.netease.nimlib.sdk.msg.model.SystemMessage
import kotlinx.android.synthetic.main.activity_message_center.*
import kotlinx.android.synthetic.main.item_message_center.view.*
import pro.haichuang.learn.home.R
import pro.haichuang.learn.home.config.BaseActivity
import pro.haichuang.learn.home.ui.activity.message.itemmodel.MessageCenterModel
import pro.haichuang.learn.home.utils.mlog


@ContentView(R.layout.activity_message_center)
class MessageCenterActivity : BaseActivity() {

    private val systemService by lazy { NIMClient.getService(SystemMessageService::class.java) }

    override fun initData() {
        titleModel.title = "消息中心"
        systemService.querySystemMessageByType(arrayListOf(SystemMessageType.AddFriend), 0, 999).setCallback(object : RequestCallback<MutableList<SystemMessage>> {
            override fun onSuccess(p0: MutableList<SystemMessage>?) {
                val data = ArrayList<MessageCenterModel>()
                p0?.forEach {
                    val item = MessageCenterModel()
                    val obj = it.attachObject as AddFriendNotify
                    item.id = it.messageId
                    item.account = obj.account
                    NimUIKit.getUserInfoProvider().getUserInfo(obj.account)?.let {
                        item.header = it.avatar?:""
                        item.name = it.name?:""
                    }
                    when (it.status) {
                        SystemMessageStatus.init -> item.status = 0
                        SystemMessageStatus.passed -> item.status = 1
                        SystemMessageStatus.declined -> item.status = 2
                    }
                    data.add(item)
                }
                listView.adapter = CommonAdapter(layoutInflater, R.layout.item_message_center, data) { v, t, _ ->
                    v.agree.setOnClickListener {
                        systemService.setSystemMessageStatus(t.id, SystemMessageStatus.passed)
                        NIMClient.getService(MsgService::class.java).sendMessage(MessageBuilder.createTextMessage(t.account, SessionTypeEnum.P2P, "我同意了你的好友请求"), false)
                        t.status = 1
                    }
                    v.reject.setOnClickListener {
                        systemService.setSystemMessageStatus(t.id, SystemMessageStatus.declined)
                        t.status = 2
                    }
                }
            }

            override fun onFailed(p0: Int) {
            }

            override fun onException(p0: Throwable?) {
            }

        })

    }

}
