package pro.haichuang.learn.home.ui.activity.index

import com.jacy.kit.adapter.CommonAdapter
import kotlinx.android.synthetic.main.activity_zhiyuan_result.*
import kotlinx.android.synthetic.main.item_zhiyuan_result.view.*
import com.jacy.kit.config.ContentView
import pro.haichuang.learn.home.config.BaseActivity
import pro.haichuang.learn.home.R
import pro.haichuang.learn.home.ui.dialog.ChooseZhiYuanPopup
import pro.haichuang.learn.home.ui.dialog.ShareDialog


@ContentView(R.layout.activity_zhiyuan_result)
class ZhiYuanResultActivity : BaseActivity() {

    override fun initData() {
        listView.adapter = CommonAdapter(layoutInflater, R.layout.item_zhiyuan_result, arrayListOf(1, 2, 3, 4, 5, 6, 7, 8)) { v, _, _ ->
            v.choose_zhiyuan.setOnClickListener {
                ChooseZhiYuanPopup(it).show()
            }
        }
    }

    override fun initListener() {
        to_share.setOnClickListener {
            ShareDialog(this).show()
        }
    }
}
