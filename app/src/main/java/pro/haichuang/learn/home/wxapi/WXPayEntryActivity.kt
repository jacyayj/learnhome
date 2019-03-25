package pro.haichuang.learn.home.wxapi

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.jacy.kit.config.toJson
import com.jacy.kit.config.toast
import com.tencent.mm.opensdk.constants.ConstantsAPI
import com.tencent.mm.opensdk.modelbase.BaseReq
import com.tencent.mm.opensdk.modelbase.BaseResp
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler
import pro.haichuang.learn.home.utils.ShareUtils
import pro.haichuang.learn.home.utils.mlog

class WXPayEntryActivity : AppCompatActivity(), IWXAPIEventHandler {

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
        when (resp.type) {
            ConstantsAPI.COMMAND_PAY_BY_WX -> {
                when (resp.errCode) {
                    BaseResp.ErrCode.ERR_OK -> {
                        toast("支付成功")
                        finish()
                    }
                    BaseResp.ErrCode.ERR_USER_CANCEL -> {
                        toast("支付取消")
                        finish()
                    }
                    else -> {
                        toast("支付失败")
                        finish()
                    }
                }
            }
        }
    }

}