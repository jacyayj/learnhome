package pro.haichuang.learn.home.ui.activity.message

import kotlinx.android.synthetic.main.activity_friend_setting.*
import com.jacy.kit.config.ContentView
import pro.haichuang.learn.home.config.BaseActivity
import pro.haichuang.learn.home.R
import pro.haichuang.learn.home.ui.dialog.NoticeDialog


@ContentView(R.layout.activity_friend_setting)
class FriendSettingActivity : BaseActivity() {

    override fun initData() {
        titleModel.title = "设置"
    }

    override fun initListener() {
        clear_history.setOnClickListener {
            NoticeDialog(this).show()
        }
    }
}
