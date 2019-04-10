package pro.haichuang.learn.home.utils

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.jacy.kit.config.toast
import com.netease.nimlib.sdk.NIMClient
import com.netease.nimlib.sdk.friend.FriendService
import com.netease.nimlib.sdk.friend.constant.FriendFieldEnum
import com.netease.nimlib.sdk.msg.MessageBuilder
import com.netease.nimlib.sdk.msg.MsgService
import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum
import com.vondear.rxtool.RxEncryptTool
import com.zhouyou.http.EasyHttp
import com.zhouyou.http.callback.SimpleCallBack
import com.zhouyou.http.exception.ApiException
import com.zhouyou.http.model.HttpParams
import pro.haichuang.learn.home.bean.Response
import pro.haichuang.learn.home.net.Url

object HttpUtils {

    fun acceptOrder(orderId: Int, account: String) {
        val params = HttpParams()
        params.put("orderId", orderId.toString())
        params.put("sessionKey", SPUtils.session)
        params.put("appId", "5698402822576322")
        params.put("nonce_str", System.currentTimeMillis().toString())
        params.put("sign", sign(params.urlParamsMap))
        EasyHttp.post(Url.Order.Accept)
                .params(params)
                .execute(object : SimpleCallBack<String>() {
                    override fun onSuccess(t: String?) {
                        val response = Gson().fromJson<Response<Any>>(t, object : TypeToken<Response<Any>>() {}.type)
                        if (response.code == 200) {
                            val extension = mapOf(Pair("orderTime", GsonUtil.getString(response.body, "acceptTime")), Pair("orderId", orderId))
                            NIMClient.getService(FriendService::class.java).updateFriendFields(account, mapOf(Pair(FriendFieldEnum.EXTENSION, extension)))
                            val msg = MessageBuilder.createTipMessage(account, SessionTypeEnum.P2P)
                            msg.content = "计费开始，本次咨询将在24小时后结束！"
                            msg.remoteExtension = extension
                            NIMClient.getService(MsgService::class.java).sendMessage(msg, false)
                        }
                    }

                    override fun onError(e: ApiException?) {
                        toast(e?.message)
                    }
                })
    }

    private fun sign(map: LinkedHashMap<String, String>): String {
        var sign = ""
        map.keys.toSortedSet().forEach {
            if ("uploadFile" != it && !map[it].isNullOrEmpty())
                sign += "$it=${map[it]}&"
        }
        sign += "key=${Url.app_key}"
        return RxEncryptTool.encryptMD5ToString(sign)
    }
}