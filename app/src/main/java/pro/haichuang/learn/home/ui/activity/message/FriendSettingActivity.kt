package pro.haichuang.learn.home.ui.activity.message

import android.content.Intent
import android.provider.Contacts.PresenceColumns.IM_ACCOUNT
import com.jacy.kit.config.ContentView
import com.jacy.kit.config.gone
import com.jacy.kit.config.toast
import com.netease.nim.uikit.api.NimUIKit
import com.netease.nim.uikit.business.recent.RecentContactsFragment.RECENT_TAG_STICKY
import com.netease.nim.uikit.business.session.helper.MessageListPanelHelper
import com.netease.nim.uikit.common.CommonUtil
import com.netease.nimlib.sdk.NIMClient
import com.netease.nimlib.sdk.RequestCallback
import com.netease.nimlib.sdk.friend.FriendService
import com.netease.nimlib.sdk.friend.constant.FriendFieldEnum
import com.netease.nimlib.sdk.msg.MsgService
import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum
import kotlinx.android.synthetic.main.activity_friend_setting.*
import pro.haichuang.learn.home.R
import pro.haichuang.learn.home.config.BaseActivity
import pro.haichuang.learn.home.ui.dialog.InputDialog
import pro.haichuang.learn.home.ui.dialog.NoticeDialog
import pro.haichuang.learn.home.utils.ImageBinding


@ContentView(R.layout.activity_friend_setting)
class FriendSettingActivity : BaseActivity() {

    private val msgService by lazy { NIMClient.getService(MsgService::class.java) }
    private val friendService by lazy { NIMClient.getService(FriendService::class.java) }
    private val account by lazy { intent.getStringExtra(IM_ACCOUNT) }
    private val contact by lazy { msgService.queryRecentContact(account, SessionTypeEnum.P2P) }
    private var isFromUser = true

    override fun initData() {
        titleModel.title = "设置"
        contact.apply {
            sticky.isChecked = CommonUtil.isTagSet(this, RECENT_TAG_STICKY)
        }
        black.isChecked = friendService.isInBlackList(account)
    }

    override fun initListener() {
        NimUIKit.getUserInfoProvider().getUserInfo(account)?.let {
            val alias = friendService.getFriendByAccount(account)?.alias
            if (friendService.isMyFriend(account)) {
                alias_tv.text = alias
                nickname.text = "昵称：${it.name}"
            } else {
                nickname.gone()
                remark.gone()
                alias_tv.text = it.name
            }
            ImageBinding.displayNet(header, it.avatar)
        }
        clear_history.setOnClickListener {
            NoticeDialog(this) {
                msgService.clearChattingHistory(account, SessionTypeEnum.P2P)
                MessageListPanelHelper.getInstance().notifyClearMessages(account)
//                sendBroadcast(Intent("refreshMessage"))
                toast("清除成功")
            }.show()
        }
        remark.setOnClickListener {
            InputDialog(this) {
                val filed = HashMap<FriendFieldEnum, String>()
                filed[FriendFieldEnum.ALIAS] = it
                friendService.updateFriendFields(account, filed as Map<FriendFieldEnum, Any>?).setCallback(object : RequestCallback<Void> {
                    override fun onSuccess(p0: Void?) {
                        toast("保存成功")
                        remark.text = it
                    }

                    override fun onFailed(p0: Int) {
                        if (p0 == 403)
                            toast("设置识别，TA还不是你的好友")
                        else
                            toast("保存失败 $p0")
                    }

                    override fun onException(p0: Throwable?) {
                        toast("保存出错 ${p0?.message}")
                    }
                })
            }.show()
        }
        sticky.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked)
                CommonUtil.addTag(contact, RECENT_TAG_STICKY)
            else
                CommonUtil.removeTag(contact, RECENT_TAG_STICKY)
            msgService.updateRecent(contact)
            sendBroadcast(Intent("refreshContact").putExtra(IM_ACCOUNT, account).putExtra("hasSticky", isChecked))
        }
        black.setOnCheckedChangeListener { _, isChecked ->
            if (isFromUser)
                if (isChecked)
                    friendService.addToBlackList(account).setCallback(object : RequestCallback<Void> {
                        override fun onSuccess(p0: Void?) {
                            toast("加入黑名单成功")
                        }

                        override fun onFailed(p0: Int) {
                            toast("加入黑名单失败 $p0")
                            isFromUser = false
                            black.isChecked = false
                        }

                        override fun onException(p0: Throwable?) {
                            toast("加入黑名单错误 ${p0?.message}")
                            isFromUser = false
                            black.isChecked = false
                        }
                    })
                else
                    friendService.removeFromBlackList(account).setCallback(object : RequestCallback<Void> {
                        override fun onSuccess(p0: Void?) {
                            toast("移除黑名单成功")
                        }

                        override fun onFailed(p0: Int) {
                            toast("移除黑名单失败 $p0")
                            isFromUser = false
                            black.isChecked = true
                        }

                        override fun onException(p0: Throwable?) {
                            toast("移除黑名单错误 ${p0?.message}")
                            isFromUser = false
                            black.isChecked = true
                        }
                    })
            else
                isFromUser = true
        }
    }
}
