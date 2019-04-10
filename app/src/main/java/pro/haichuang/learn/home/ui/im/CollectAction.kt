package pro.haichuang.learn.home.ui.im

import android.content.Intent
import com.jacy.kit.config.mStartActivityForResult
import com.netease.nim.uikit.business.session.actions.BaseAction
import com.netease.nim.uikit.business.session.constant.RequestCode
import com.netease.nimlib.sdk.msg.MessageBuilder
import pro.haichuang.learn.home.R
import pro.haichuang.learn.home.im.CollectAttachment
import pro.haichuang.learn.home.ui.activity.mine.CollectActivity

class CollectAction : BaseAction(R.drawable.shouc, R.string.collect) {


    override fun onClick() {
        activity.mStartActivityForResult(CollectActivity::class.java, makeRequestCode(RequestCode.SEND_CUSTOM_MESSAGE))
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val attachment = CollectAttachment()
        attachment.header = ""
        attachment.name = "大鹏"
        attachment.title = "自定义文字消息"
        attachment.image = ""
        attachment.text = "这是一条测试的自定义消息,他将展示自定义消息没有图片时候的样子"
        val attachment2 = CollectAttachment()
        attachment2.header = ""
        attachment2.name = "大鹏"
        attachment2.title = "自定义图片消息"
        attachment2.image = "test.jpg"
        sendMessage(MessageBuilder.createCustomMessage(account, sessionType, attachment))
        sendMessage(MessageBuilder.createCustomMessage(account, sessionType, attachment2))
    }
}