package pro.haichuang.learn.home.ui.activity.index

import com.jacy.kit.config.mStartActivity
import kotlinx.android.synthetic.main.activity_zhuanti_details.*
import pro.haichuang.learn.home.R
import com.jacy.kit.config.ContentView
import pro.haichuang.learn.home.config.BaseActivity
import pro.haichuang.learn.home.ui.dialog.ShareDialog


@ContentView(R.layout.activity_zhuanti_details)
class ZhuanTiDetailsActivity : BaseActivity() {

    override fun initData() {
    }


    override fun initListener() {
        to_share.setOnClickListener { ShareDialog(this).show() }
        confirm.setOnClickListener { mStartActivity(ZhuanTiSignupActivity::class.java) }
    }
}
