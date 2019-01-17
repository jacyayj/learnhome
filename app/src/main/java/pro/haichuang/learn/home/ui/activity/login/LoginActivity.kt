package pro.haichuang.learn.home.ui.activity.login

import android.support.design.widget.TabLayout
import android.view.View
import com.jacy.kit.config.mStartActivity
import kotlinx.android.synthetic.main.activity_login.*
import pro.haichuang.learn.home.R
import pro.haichuang.learn.home.annotation.ContentView
import pro.haichuang.learn.home.config.DataBindingActivity
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
            mStartActivity(MainActivity::class.java)
        }
    }

    fun tourIn(view: View) {
        mStartActivity(MainActivity::class.java)
    }
}
