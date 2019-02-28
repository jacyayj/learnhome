package pro.haichuang.learn.home.ui.activity.login

import android.support.design.widget.TabLayout
import android.view.View
import com.jacy.kit.config.ContentView
import com.jacy.kit.config.mStartActivity
import com.jacy.kit.config.toast
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

@ContentView(R.layout.activity_login)
class LoginActivity : DataBindingActivity<LoginModel>() {

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
            mStartActivity(CompleteInfoActivity::class.java)
        }
        to_wechat.setOnClickListener {
            mStartActivity(CompleteInfoActivity::class.java)
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

        fetch_sms.setOnClickListener {
            toast("开始发送")
            val params = HttpParams()
            params.put("username", "18384124448")
            params.put("password", "1")
            post<String>(Url.Sms.Send, params)
        }
    }

    override fun onSuccess(url: String, result: Any?) {
        toast("发送成功")
    }

    fun tourIn(view: View) {
        mStartActivity(MainActivity::class.java)
    }
}
