package pro.haichuang.learn.home.ui.activity.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.view.View
import com.jacy.kit.config.ContentView
import com.jacy.kit.config.mStartActivity
import com.jacy.kit.config.toJson
import com.jacy.kit.config.toast
import com.tencent.connect.share.QQShare
import com.tencent.mm.opensdk.modelmsg.WXAppExtendObject
import com.tencent.tauth.IUiListener
import com.tencent.tauth.Tencent
import com.tencent.tauth.UiError
import com.zhouyou.http.EasyHttp
import com.zhouyou.http.callback.SimpleCallBack
import com.zhouyou.http.exception.ApiException
import com.zhouyou.http.model.ApiResult
import com.zhouyou.http.model.HttpParams
import kotlinx.android.synthetic.main.activity_login.*
import pro.haichuang.learn.home.R
import pro.haichuang.learn.home.config.DataBindingActivity
import pro.haichuang.learn.home.net.Url
import pro.haichuang.learn.home.ui.activity.MainActivity
import pro.haichuang.learn.home.ui.activity.login.viewmodel.LoginModel
import pro.haichuang.learn.home.utils.ShareUtils
import pro.haichuang.learn.home.utils.mlog

@ContentView(R.layout.activity_login)
class LoginActivity : DataBindingActivity<LoginModel>(), IUiListener {

    override fun initData() {
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
            ShareUtils.loginToQQ(this,this)
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
            mStartActivity(MainActivity::class.java)
        }
        confirm_normal.setOnClickListener {
            autoPost<String>(Url.User.Login)
        }

        fetch_sms.requestCode = {
            autoPost<String>(Url.Sms.Send)
        }

        pwd_toggle.setEdit(pwd)
        clear.setEdit(phone)
    }

    override fun onSuccess(url: String, result: Any?) {
        when (url) {
            Url.Sms.Send -> {
                fetch_sms.notifyCount()
                model.code = result.toString()
                toast("发送成功")
            }
            Url.User.Login -> {
                toast("登录成功")
                mStartActivity(MainActivity::class.java)
                finish()
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
        Tencent.handleResultData(data,this)
    }
}
