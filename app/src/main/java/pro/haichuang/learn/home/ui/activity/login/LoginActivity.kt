package pro.haichuang.learn.home.ui.activity.login

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.support.design.widget.TabLayout
import android.view.View
import com.jacy.kit.config.ContentView
import com.jacy.kit.config.mStartActivity
import com.jacy.kit.config.toast
import com.netease.nim.uikit.api.NimUIKit
import com.netease.nimlib.sdk.RequestCallback
import com.netease.nimlib.sdk.auth.LoginInfo
import com.tencent.tauth.IUiListener
import com.tencent.tauth.Tencent
import com.tencent.tauth.UiError
import com.vondear.rxtool.RxPermissionsTool
import com.zhouyou.http.model.HttpParams
import kotlinx.android.synthetic.main.activity_login.*
import org.json.JSONObject
import pro.haichuang.learn.home.R
import pro.haichuang.learn.home.bean.UserInfo
import pro.haichuang.learn.home.config.DataBindingActivity
import pro.haichuang.learn.home.net.Url
import pro.haichuang.learn.home.ui.activity.MainActivity
import pro.haichuang.learn.home.ui.activity.login.viewmodel.LoginModel
import pro.haichuang.learn.home.utils.GsonUtil
import pro.haichuang.learn.home.utils.SPUtils
import pro.haichuang.learn.home.utils.ShareUtils
import pro.haichuang.learn.home.utils.mlog

@ContentView(R.layout.activity_login)
class LoginActivity : DataBindingActivity<LoginModel>(), IUiListener {

    private val re_login by lazy { intent.getBooleanExtra("re_login", false) }

    private var openId = ""
    private var source = ""

    private val receiver = WxResponse()

    override fun initData() {
        val permission = RxPermissionsTool.with(this)
                .addPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
                .addPermission(Manifest.permission.READ_PHONE_STATE)
                .addPermission(Manifest.permission.CAMERA)
                .addPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .initPermission()
        titleModel.showLeft = false
        titleModel.showRight = true
        titleModel.showBottomeLine = false
        titleModel.titleRightText = "注册"
        registerReceiver(receiver, IntentFilter("wx_login_response"))

        if (permission.isEmpty())
            SPUtils.session?.let {
                mStartActivity(MainActivity::class.java)
                finish()
            }
    }

    override fun initListener() {
        tab.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(p0: TabLayout.Tab?) {

            }

            override fun onTabUnselected(p0: TabLayout.Tab?) {

            }

            override fun onTabSelected(p0: TabLayout.Tab?) {
                model.fastLogin = p0?.position == 1
            }
        })
        titleModel.onRightClick = {
            mStartActivity(RegisterActivity::class.java)
        }
        to_qq.setOnClickListener {
            ShareUtils.loginToQQ(this, this)
        }
        to_wechat.setOnClickListener {
            ShareUtils.loginToWx()
        }
        to_forget.setOnClickListener {
            mStartActivity(ModifyPwdActivity::class.java)
        }
        confirm_fast.setOnClickListener {
            if (model.checkSuccess(Url.User.Login)) {
                post(Url.User.Login, HttpParams("username", model.phone).apply {
                    put("smsCode", model.code)
                }, jessionid = model.JSESSIONID)
            }

        }
        confirm_normal.setOnClickListener {
            model.needEncrypt = "18384124448" != model.user
            autoPost(Url.User.Login)
        }

        fetch_sms.requestCode = {
            autoPost(Url.Sms.Send)
        }

        pwd_toggle.setEdit(pwd)
        clear.setEdit(phone)
    }

    override fun onSuccess(url: String, result: Any?) {
        when (url) {
            Url.Sms.Send -> {
                fetch_sms.notifyCount()
                toast("发送成功")
            }
            Url.User.Login, Url.User.ThirdLogin -> {
                if (openId.isNotEmpty() && result is Int && result == 303) {
                    mStartActivity(CompleteInfoActivity::class.java, Pair("thirdKey", openId), Pair("source", source))
                } else {
                    val info = GsonUtil.parseObject(result, UserInfo::class.java)
                    SPUtils.userInfo = info
                    NimUIKit.login(LoginInfo(info.imAccid, info.imToken), object : RequestCallback<LoginInfo> {
                        override fun onSuccess(p0: LoginInfo?) {
                            SPUtils.loginInfo = p0
                            SPUtils.phone = model.phone
                            NimUIKit.getOptions().isTeacher = info.teacher
                            NimUIKit.loginSuccess(p0?.account)
                            toast("登录成功")
                            if (!re_login)
                                mStartActivity(MainActivity::class.java)
                            finish()
                        }

                        override fun onFailed(p0: Int) {
                            toast("登录失败   $p0")
                        }

                        override fun onException(p0: Throwable?) {
                        }
                    })
                }
            }
        }
    }

    fun tourIn(view: View) {
        mStartActivity(MainActivity::class.java)
    }

    override fun onComplete(result: Any?) {
        result?.let {
            if (result is JSONObject) {
                openId = result.getString("openid")
                source = "QQ"
                val token = result.getString("access_token")
                val expires = result.getString("expires_in")
                ShareUtils.setOpenId(openId, token, expires)
                ShareUtils.fetchQQInfo(this) {
                    mlog.v("userInfo : $it")
                    post(Url.User.ThirdLogin, HttpParams("source", "QQ").apply {
                        put("thirdKey", openId)
                        put("accountInfo", it)
                    })
                }
            }
        }
    }

    override fun onCancel() {
    }

    override fun onError(p0: UiError?) {
        mlog.v("onError : ${p0?.errorCode}")
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        grantResults.forEach {
            if (it == PackageManager.PERMISSION_DENIED) {
                return
            }
        }
        SPUtils.session?.let {
            mStartActivity(MainActivity::class.java)
            finish()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Tencent.handleResultData(data, this)
    }

    inner class WxResponse : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            intent?.let {
                openId = it.getStringExtra("openId")
                source = "WEIXIN"
                post(Url.User.ThirdLogin, HttpParams("source", "WEIXIN").apply {
                    put("thirdKey", openId)
                    put("accountInfo", it.getStringExtra("userInfo"))
                })
            }
        }
    }

    override fun onDestroy() {
        unregisterReceiver(receiver)
        super.onDestroy()
    }
}
