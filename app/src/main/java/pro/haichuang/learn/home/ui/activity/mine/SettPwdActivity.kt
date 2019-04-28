package pro.haichuang.learn.home.ui.activity.mine

import kotlinx.android.synthetic.main.activity_sett_pwd.*
import pro.haichuang.learn.home.R
import com.jacy.kit.config.ContentView
import com.jacy.kit.config.toast
import pro.haichuang.learn.home.config.DataBindingActivity
import pro.haichuang.learn.home.net.Url
import pro.haichuang.learn.home.ui.activity.mine.viewmodel.SettPwdModel
import pro.haichuang.learn.home.utils.SPUtils
import pro.haichuang.learn.home.utils.mlog


@ContentView(R.layout.activity_sett_pwd)
class SettPwdActivity : DataBindingActivity<SettPwdModel>() {


    override fun initData() {
        titleModel.title = if (SPUtils.hasPayPassword)"修改支付密码" else "设置支付密码"
        if (!SPUtils.hasPayPassword)
            model.step = 1
    }

    override fun initListener() {
        pwd.setOnTextChangeListener { s, b ->
            if (b)
                when (model.step) {
                    0 -> {
                        model.originPwd = s
                        model.step++
                        pwd.setText("")
                    }
                    1 -> {
                        model.newPwd = s
                        model.step++
                        mlog.v("new:"+model.newPwd)
                        pwd.setText("")
                    }
                    2 -> {
                        model.confirmPwd = s
                        mlog.v("confirm:"+model.newPwd)
                        model.needEncrypt = false
                        autoPost(Url.User.PayPassword, needSession = true)
                    }
                }
        }
    }

    override fun onSuccess(url: String, result: Any?) {
        toast(if (SPUtils.hasPayPassword) "修改成功" else "设置成功")
        finish()
    }

    override fun onBackPressed() {
        if (model.step == if (SPUtils.hasPayPassword) 0 else 1)
            super.onBackPressed()
        else {
            pwd.setText("")
            model.step--
        }
    }
}
