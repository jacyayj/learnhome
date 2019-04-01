package pro.haichuang.learn.home.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.netease.nimlib.sdk.NIMClient
import com.netease.nimlib.sdk.friend.FriendService
import com.netease.nimlib.sdk.friend.constant.FriendFieldEnum
import com.zhouyou.http.EasyHttp
import com.zhouyou.http.callback.SimpleCallBack
import com.zhouyou.http.exception.ApiException
import pro.haichuang.learn.home.bean.Response
import pro.haichuang.learn.home.net.Url
import pro.haichuang.learn.home.utils.GsonUtil
import pro.haichuang.learn.home.utils.SPUtils

class FriendReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        intent?.let {
            val orderId = it.getIntExtra("orderId", -1)
            val account = it.getStringExtra("account")
            EasyHttp.post(Url.Order.Accept)
                    .params("orderId", orderId.toString())
                    .params("sessionKey", SPUtils.session)
                    .execute(object : SimpleCallBack<String>() {
                        override fun onSuccess(t: String?) {
                            val response = Gson().fromJson<Response<Any>>(t, object : TypeToken<Response<Any>>() {}.type)
                            if (response.code == 200) {
                                val extension = mapOf(Pair("orderTime", GsonUtil.getString(response.body, "acceptTime")), Pair("orderId", orderId))
                                NIMClient.getService(FriendService::class.java).updateFriendFields(account, mapOf(Pair(FriendFieldEnum.EXTENSION, extension)))
                            }
                        }

                        override fun onError(e: ApiException?) {

                        }
                    })

        }

    }
}