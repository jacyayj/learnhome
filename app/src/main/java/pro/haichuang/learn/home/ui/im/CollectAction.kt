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
        activity.mStartActivityForResult(CollectActivity::class.java, makeRequestCode(RequestCode.SEND_CUSTOM_MESSAGE),Pair("isChat",true))
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        data?.let {
            sendMessage(MessageBuilder.createCustomMessage(account, sessionType, it.getSerializableExtra("attachment") as CollectAttachment))
        }
    }
}