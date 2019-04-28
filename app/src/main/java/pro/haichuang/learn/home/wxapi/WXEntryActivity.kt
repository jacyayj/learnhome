package pro.haichuang.learn.home.wxapi

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.jacy.kit.config.toJson
import com.tencent.mm.opensdk.modelbase.BaseReq
import com.tencent.mm.opensdk.modelbase.BaseResp
import com.tencent.mm.opensdk.modelmsg.SendAuth
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler
import pro.haichuang.learn.home.utils.HttpUtils
import pro.haichuang.learn.home.utils.ShareUtils
import pro.haichuang.learn.home.utils.mlog


class WXEntryActivity : AppCompatActivity(), IWXAPIEventHandler {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ShareUtils.wxApi.handleIntent(intent, this)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        this.intent = intent
        ShareUtils.wxApi.handleIntent(intent, this)
    }

    override fun onReq(p0: BaseReq?) {
        mlog.v("onReq : " + p0?.toJson())
    }


    override fun onResp(resp: BaseResp) {
        mlog.v("onResp : " + resp.toJson())
        when (resp.errCode) {
            BaseResp.ErrCode.ERR_OK -> {
                val code = (resp as SendAuth.Resp).code //即为所需的code}
                HttpUtils.fetWxUserInfo(code){openId, userInfo ->
                    sendBroadcast(Intent("wx_login_response")
                            .putExtra("openId", openId)
                            .putExtra("userInfo", userInfo)
                    )
                }
                finish()
            }
        }
    }

}