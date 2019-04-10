package pro.haichuang.learn.home.ui.activity.mine

import android.app.Activity
import com.jacy.kit.config.ContentView
import kotlinx.android.synthetic.main.activity_vr.*
import pro.haichuang.learn.home.R
import pro.haichuang.learn.home.adapter.CollectAdapter
import pro.haichuang.learn.home.config.BaseActivity


@ContentView(R.layout.activity_collect)
class CollectActivity : BaseActivity() {

    override fun initData() {
        listView.adapter = CollectAdapter(this)
    }

    override fun initListener() {
        listView.setOnItemClickListener { _, _, position, _ ->
            setResult(Activity.RESULT_OK)
            finish()
        }
    }
}
