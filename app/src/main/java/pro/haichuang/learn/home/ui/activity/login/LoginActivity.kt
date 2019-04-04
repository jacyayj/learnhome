package pro.haichuang.learn.home.ui.activity.login

import android.content.Intent
import android.support.design.widget.TabLayout
import android.view.View
import com.jacy.kit.config.ContentView
import com.jacy.kit.config.mStartActivity
import com.jacy.kit.config.toJson
import com.jacy.kit.config.toast
import com.netease.nim.uikit.api.NimUIKit
import com.netease.nimlib.sdk.RequestCallback
import com.netease.nimlib.sdk.auth.LoginInfo
import com.tencent.tauth.IUiListener
import com.tencent.tauth.Tencent
import com.tencent.tauth.UiError
import com.zhouyou.http.model.HttpParams
import kotlinx.android.synthetic.main.activity_login.*
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

    override fun initData() {
        SPUtils.session?.let {
            mStartActivity(MainActivity::class.java)
            finish()
        }
        titleModel.showLeft = false
        titleModel.showRight = true
        titleModel.showBottomeLine = false
        titleModel.titleRightText = "注册"
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
            //            ShareUtils.shareToQQ(this)
            ShareUtils.loginToQQ(this, this)
//            mStartActivity(CompleteInfoActivity::class.java)
        }
        to_wechat.setOnClickListener {
            //            ShareUtils.shareToWx(this)
//            ShareUtils.shareToCircle(this)
            ShareUtils.loginToWx()
//            mStartActivity(CompleteInfoActivity::class.java)
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
            Url.User.Login -> {
                val info = GsonUtil.parseObject(result, UserInfo::class.java)
                SPUtils.userInfo = info
                NimUIKit.login(LoginInfo(info.imAccid, info.imToken), object : RequestCallback<LoginInfo> {
                    override fun onSuccess(p0: LoginInfo?) {
                        SPUtils.loginInfo = p0
                        SPUtils.phone = model.phone
                        NimUIKit.getOptions().isTeacher = info.teacher
                        NimUIKit.loginSuccess(p0?.account)
                        toast("登录成功")
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

    fun tourIn(view: View) {
        mStartActivity(MainActivity::class.java)
    }

    override fun onComplete(p0: Any?) {
        toast("登录成功")
        mlog.v("onComplete : ${p0?.toJson()}")
    }

    override fun onCancel() {
    }

    override fun onError(p0: UiError?) {
        mlog.v("onError : ${p0?.errorCode}")
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Tencent.handleResultData(data, this)
    }
}
