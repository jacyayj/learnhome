package pro.haichuang.learn.home.ui.activity.mine

import android.app.Activity
import android.content.Intent
import com.jacy.kit.config.ContentView
import com.jacy.kit.config.toast
import com.netease.nimlib.sdk.NIMClient
import com.netease.nimlib.sdk.RequestCallback
import com.netease.nimlib.sdk.uinfo.UserService
import com.netease.nimlib.sdk.uinfo.constant.UserInfoFieldEnum
import com.zhouyou.http.model.HttpParams
import kotlinx.android.synthetic.main.activity_modify_name.*
import pro.haichuang.learn.home.R
import pro.haichuang.learn.home.config.BaseActivity
import pro.haichuang.learn.home.net.Url


@ContentView(R.layout.activity_modify_name)
class ModifyNameActivity : BaseActivity() {

    override fun initData() {
        titleModel.title = "用户名"
        input.hint = intent.getStringExtra("name")
    }

    override fun initListener() {
        save.setOnClickListener {
            val params = HttpParams()
            params.put("realname", input.text.toString())
            post(Url.User.UpdateInfo, params,showLoading = true, needSession = true)
        }
    }

    override fun onSuccess(url: String, result: Any?) {
        val filed = HashMap<UserInfoFieldEnum, String>()
        filed[UserInfoFieldEnum.Name] = input.text.toString()
        NIMClient.getService(UserService::class.java).updateUserInfo(filed as Map<UserInfoFieldEnum, Any>?).setCallback(object : RequestCallback<Void> {
            override fun onSuccess(p0: Void?) {
                toast("更新成功")
                setResult(Activity.RESULT_OK, Intent().putExtra("name", input.text.toString()))
                finish()
            }

            override fun onFailed(p0: Int) {
                toast("更新失败 $p0")
            }

            override fun onException(p0: Throwable?) {
                toast("更新出错 ${p0?.message}")
            }
        })

    }
}
