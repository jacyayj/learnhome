package pro.haichuang.learn.home.ui.activity.mine

import com.jacy.kit.adapter.CommonAdapter
import com.jacy.kit.config.mStartActivity
import kotlinx.android.synthetic.main.activity_message_center.*
import kotlinx.android.synthetic.main.item_file.view.*
import com.jacy.kit.config.ContentView
import pro.haichuang.learn.home.config.BaseActivity
import pro.haichuang.learn.home.R
import pro.haichuang.learn.home.ui.activity.CityListActivity
import pro.haichuang.learn.home.ui.dialog.AddressDialog
import pro.haichuang.learn.home.ui.dialog.ClassDialog
import pro.haichuang.learn.home.utils.DataUtils


@ContentView(R.layout.activity_file)
class FileActivity : BaseActivity() {

    override fun initData() {
        titleModel.title = "个人档案"
        listView.adapter = CommonAdapter(layoutInflater, R.layout.item_file, DataUtils.formatFileData()) { v, t, i ->
            if (t.canChoose) {
                v.content.setOnClickListener {
                    when (i) {
                        1 -> mStartActivity(CityListActivity::class.java)
                        2 -> AddressDialog(this).show()
                        5 -> ClassDialog(this).show()
                    }
                }
            }
        }
    }

}
