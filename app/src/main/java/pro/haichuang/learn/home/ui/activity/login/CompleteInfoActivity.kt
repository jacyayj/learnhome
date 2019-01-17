package pro.haichuang.learn.home.ui.activity.login

import com.jacy.kit.config.mStartActivity
import kotlinx.android.synthetic.main.activity_complete_info.*
import pro.haichuang.learn.home.R
import pro.haichuang.learn.home.annotation.ContentView
import pro.haichuang.learn.home.config.DataBindingActivity
import pro.haichuang.learn.home.ui.activity.MainActivity
import pro.haichuang.learn.home.ui.activity.login.viewmodel.CompleteInfoModel


@ContentView(R.layout.activity_complete_info)
class CompleteInfoActivity : DataBindingActivity<CompleteInfoModel>() {

    override fun initData() {
        titleModel.title = "绑定手机号"
        titleModel.titleLeftText = "返回"
        titleModel.showBottomeLine = false
    }

    override fun initListener() {
        confirm.setOnClickListener {
            if (model.verify)
                mStartActivity(MainActivity::class.java)
            else {
                model.verify = true
                titleModel.title = "设置密码"
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
