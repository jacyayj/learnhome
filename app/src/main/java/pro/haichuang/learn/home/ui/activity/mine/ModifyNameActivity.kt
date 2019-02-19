package pro.haichuang.learn.home.ui.activity.mine

import android.app.Activity
import android.content.Intent
import kotlinx.android.synthetic.main.activity_modify_name.*
import kotlinx.android.synthetic.main.fragment_mine.view.*
import com.jacy.kit.config.ContentView
import pro.haichuang.learn.home.config.BaseActivity
import pro.haichuang.learn.home.R


@ContentView(R.layout.activity_modify_name)
class ModifyNameActivity : BaseActivity() {

    override fun initData() {
        titleModel.title="用户名"
        input.hint = intent.getStringExtra("name")
    }

    override fun initListener() {
        save.setOnClickListener {
            setResult(Activity.RESULT_OK, Intent().putExtra("name",input.text.toString()))
            finish()
        }
    }
}
