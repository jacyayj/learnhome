package pro.haichuang.learn.home.ui.activity.mine

import android.app.Activity
import android.content.Intent
import com.jacy.kit.config.ContentView
import com.jacy.kit.config.mStartActivity
import com.jacy.kit.config.mStartActivityForResult
import com.jacy.kit.config.toast
import kotlinx.android.synthetic.main.activity_bind_vip.*
import pro.haichuang.learn.home.R
import pro.haichuang.learn.home.config.Constants.PRICE
import pro.haichuang.learn.home.config.DataBindingActivity
import pro.haichuang.learn.home.net.Url
import pro.haichuang.learn.home.ui.activity.login.LoginActivity
import pro.haichuang.learn.home.ui.activity.mine.viewmodel.BindVipModel
import pro.haichuang.learn.home.utils.GsonUtil
import pro.haichuang.learn.home.utils.SPUtils


@ContentView(R.layout.activity_bind_vip)
class BindVipActivity : DataBindingActivity<BindVipModel>() {

    override fun initData() {
        titleModel.title = "绑定新卡"
        post(Url.Account.Fee)
    }

    override fun onSuccess(url: String, result: Any?) {
        when (url) {
            Url.Account.Fee -> model.price = GsonUtil.getString(result, "vipFee")
            Url.Account.Activate -> {
                toast("激活成功")
                SPUtils.isVip = true
                finish()
            }
        }
    }

    override fun initListener() {
        buy.setOnClickListener {
            if (SPUtils.isTourist) {
                mStartActivity(LoginActivity::class.java, Pair("re_login", true))
                toast("请先登录后再操作")
            } else
                mStartActivityForResult(PaymentActivity::class.java, 0x01, Pair(PRICE, model.price))
        }
        upgrade.setOnClickListener {
            autoPost(Url.Account.Activate, needSession = true)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            toast("购买成功")
            SPUtils.isVip = true
            finish()
        }
    }
}
