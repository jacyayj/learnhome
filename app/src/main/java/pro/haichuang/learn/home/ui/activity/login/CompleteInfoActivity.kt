package pro.haichuang.learn.home.ui.activity.login

import com.jacy.kit.config.mStartActivity
import kotlinx.android.synthetic.main.activity_complete_info.*
import pro.haichuang.learn.home.R
import com.jacy.kit.config.ContentView
import com.jacy.kit.config.toast
import com.netease.nim.uikit.api.NimUIKit
import com.netease.nimlib.sdk.RequestCallback
import com.netease.nimlib.sdk.auth.LoginInfo
import com.vondear.rxtool.RxActivityTool
import pro.haichuang.learn.home.bean.UserInfo
import pro.haichuang.learn.home.config.DataBindingActivity
import pro.haichuang.learn.home.net.Url
import pro.haichuang.learn.home.ui.activity.MainActivity
import pro.haichuang.learn.home.ui.activity.login.viewmodel.CompleteInfoModel
import pro.haichuang.learn.home.utils.GsonUtil
import pro.haichuang.learn.home.utils.SPUtils


@ContentView(R.layout.activity_complete_info)
class CompleteInfoActivity : DataBindingActivity<CompleteInfoModel>() {

    override fun initData() {
        model.thirdKey = intent.getStringExtra("thirdKey")
        titleModel.title = "绑定手机号"
        titleModel.titleLeftText = "返回"
        titleModel.showBottomeLine = false
    }

    override fun initListener() {
        confirm.setOnClickListener {
            //            if (model.verify)
//                mStartActivity(MainActivity::class.java)
//            else {
//                model.verify = true
//                titleModel.title = "设置密码"
//            }
            autoPost(Url.User.ThirdBind)
        }

        fetch_sms.requestCode = {
            autoPost(Url.Sms.Send)
        }
        clear_phone.setOnClickListener {
            model.phone = ""
        }
    }

    override fun onSuccess(url: String, result: Any?) {
        when (url) {
            Url.User.ThirdBind -> {
                val info = GsonUtil.parseObject(result, UserInfo::class.java)
                SPUtils.userInfo = info
                NimUIKit.login(LoginInfo(info.imAccid, info.imToken), object : RequestCallback<LoginInfo> {
                    override fun onSuccess(p0: LoginInfo?) {
                        SPUtils.loginInfo = p0
                        SPUtils.phone = model.phone
                        NimUIKit.getOptions().isTeacher = info.teacher
                        NimUIKit.loginSuccess(p0?.account)
                        toast("登录成功")
                        RxActivityTool.skipActivityAndFinishAll(this@CompleteInfoActivity, MainActivity::class.java)
                        finish()
                    }

                    override fun onFailed(p0: Int) {
                        toast("登录失败   $p0")
                    }

                    override fun onException(p0: Throwable?) {
                    }
                })
            }
            Url.Sms.Send -> {
                fetch_sms.notifyCount()
                toast("发送成功")
            }
        }
    }

    override fun onBackPressed() {
        if (model.verify) {
            titleModel.title = "绑定手机号"
            model.verify = false
        } else
            super.onBackPressed()
    }
}
