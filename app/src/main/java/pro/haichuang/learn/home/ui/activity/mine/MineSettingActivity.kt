package pro.haichuang.learn.home.ui.activity.mine

import android.app.Activity
import android.content.Intent
import com.jacy.kit.adapter.CommonAdapter
import com.jacy.kit.config.ContentView
import com.vondear.rxtool.RxActivityTool
import kotlinx.android.synthetic.main.activity_mine_setting.*
import pro.haichuang.learn.home.R
import pro.haichuang.learn.home.config.BaseActivity
import pro.haichuang.learn.home.ui.activity.login.LoginActivity
import pro.haichuang.learn.home.ui.activity.login.ModifyPwdActivity
import pro.haichuang.learn.home.ui.dialog.NoticeDialog
import pro.haichuang.learn.home.utils.DataUtils
import pro.haichuang.learn.home.utils.SPUtils


@ContentView(R.layout.activity_mine_setting)
class MineSettingActivity : BaseActivity() {
    private val data by lazy { DataUtils.formatMineSettingData() }
    override fun initData() {
        titleModel.title = "设置"
        listView.adapter = CommonAdapter(layoutInflater, R.layout.item_mine_setting, data)
    }

    override fun initListener() {
        listView.setOnItemClickListener { _, _, position, _ ->
            when (position) {
                0 -> startActivityForResult(Intent(this, ModifyNameActivity::class.java).putExtra("name", data[position].subText), 0x01)
                1 -> startActivity(Intent(this, SettPwdActivity::class.java))
                2 -> startActivity(Intent(this, ModifyPwdActivity::class.java))
                3 -> NoticeDialog(this).show("确定清除缓存？")
                4 -> startActivity(Intent(this, FeedbackActivity::class.java))
                5 -> startActivity(Intent(this, QuestionActivity::class.java))
                6 -> startActivity(Intent(this, AboutActivity::class.java))
            }
        }
        login_out.setOnClickListener {
            SPUtils.clear()
            RxActivityTool.skipActivityAndFinishAll(this, LoginActivity::class.java)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, i: Intent?) {
        super.onActivityResult(requestCode, resultCode, i)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                0x01 -> i?.getStringExtra("name")?.let { if (it.isNotEmpty()) data[0].subText = it }
            }
        }
    }
}
