package pro.haichuang.learn.home.ui.activity.mine

import android.app.Activity
import android.content.Intent
import com.jacy.kit.config.ContentView
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
        setResult(Activity.RESULT_OK, Intent().putExtra("name", input.text.toString()))
        finish()
    }
}
