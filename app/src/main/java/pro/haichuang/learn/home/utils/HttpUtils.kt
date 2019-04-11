package pro.haichuang.learn.home.utils

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.jacy.kit.config.toJson
import com.jacy.kit.config.toast
import com.netease.nim.uikit.api.NimUIKit
import com.netease.nimlib.sdk.NIMClient
import com.netease.nimlib.sdk.RequestCallback
import com.netease.nimlib.sdk.friend.FriendService
import com.netease.nimlib.sdk.friend.constant.FriendFieldEnum
import com.netease.nimlib.sdk.friend.constant.VerifyType
import com.netease.nimlib.sdk.friend.model.AddFriendData
import com.netease.nimlib.sdk.msg.MessageBuilder
import com.netease.nimlib.sdk.msg.MsgService
import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum
import com.netease.nimlib.sdk.msg.model.CustomNotification
import com.vondear.rxtool.RxEncryptTool
import com.zhouyou.http.EasyHttp
import com.zhouyou.http.callback.SimpleCallBack
import com.zhouyou.http.exception.ApiException
import com.zhouyou.http.model.HttpParams
import pro.haichuang.learn.home.bean.Response
import pro.haichuang.learn.home.net.Url


object HttpUtils {
    private val friendService by lazy { NIMClient.getService(FriendService::class.java) }

    fun acceptOrder(context: Context, orderId: Int, account: String) {
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
                            val orderTime = GsonUtil.getString(response.body, "acceptTime")
                            updateOrderTime(context, account, orderTime, orderId)

                            val extension = mapOf(Pair("orderTime", orderTime), Pair("orderId", orderId))
                            NIMClient.getService(FriendService::class.java).updateFriendFields(account, mapOf(Pair(FriendFieldEnum.EXTENSION, extension)))
                            val msg = MessageBuilder.createTipMessage(account, SessionTypeEnum.P2P)
                            msg.content = "计费开始，本次咨询将在24小时后结束！"
                            msg.remoteExtension = extension
                            NIMClient.getService(MsgService::class.java).sendMessage(msg, false)

                            val notification = CustomNotification()
                            notification.sessionId = account
                            notification.sessionType = SessionTypeEnum.P2P
                            notification.isSendToOnlineUserOnly = false
                            notification.content = extension.toJson()
                            NIMClient.getService(MsgService::class.java).sendCustomNotification(notification)
                        }
                    }

                    override fun onError(e: ApiException?) {
                        toast(e?.message)
                    }
                })
    }

    fun updateOrderTime(context: Context, accid: String, orderTime: String, orderId: Int, needToChat: Boolean = false) {
        if (friendService.isMyFriend(accid))
            friendService.updateFriendFields(accid, mapOf(Pair(FriendFieldEnum.EXTENSION, mapOf(Pair("orderTime", orderTime), Pair("orderId", orderId))))).setCallback(object : RequestCallback<Void> {
                override fun onSuccess(p0: Void?) {
                    if (needToChat)
                        NimUIKit.startP2PSession(context, accid)
                }

                override fun onFailed(p0: Int) {
                }

                override fun onException(p0: Throwable?) {
                }
            })
        else
            addFriend(accid) {
                updateOrderTime(context, accid, orderTime, orderId)
            }
    }


    private fun addFriend(accid: String, result: () -> Unit) {
        friendService.addFriend(AddFriendData(accid, VerifyType.DIRECT_ADD)).setCallback(object : RequestCallback<Void> {
            override fun onSuccess(p0: Void?) {
                result()
            }

            override fun onFailed(p0: Int) {

            }

            override fun onException(p0: Throwable?) {
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