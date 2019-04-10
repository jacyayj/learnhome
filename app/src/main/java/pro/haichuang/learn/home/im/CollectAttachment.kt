package pro.haichuang.learn.home.im

import com.google.gson.Gson
import com.netease.nimlib.sdk.msg.attachment.MsgAttachment

class CollectAttachment : MsgAttachment {

    var header = ""

    var name = ""

    var title = ""

    var image = ""

    var text = ""

    override fun toJson(p0: Boolean): String = Gson().toJson(this)
}