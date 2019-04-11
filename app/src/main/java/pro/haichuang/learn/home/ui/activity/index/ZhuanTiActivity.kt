package pro.haichuang.learn.home.ui.activity.index

import com.jacy.kit.adapter.CommonAdapter
import com.jacy.kit.config.ContentView
import com.jacy.kit.config.mStartActivity
import kotlinx.android.synthetic.main.activity_zhuanti.*
import pro.haichuang.learn.home.R
import pro.haichuang.learn.home.config.BaseActivity
import pro.haichuang.learn.home.config.Constants
import pro.haichuang.learn.home.net.Url
import pro.haichuang.learn.home.ui.activity.index.itemmodel.ItemZhuanTiModel
import pro.haichuang.learn.home.utils.GsonUtil


@ContentView(R.layout.activity_zhuanti)
class ZhuanTiActivity : BaseActivity() {

    private val adapter by lazy { CommonAdapter<ItemZhuanTiModel>(layoutInflater, R.layout.item_zhuanti) }

    override fun initData() {
        titleModel.title = "线下讲座"
        listView.adapter = adapter
        pageUrl = Url.Lecture.List
        fetchPageData()
    }

    override fun initListener() {
        listView.setOnItemClickListener { _, _, position, _ ->
            mStartActivity(ZhuanTiDetailsActivity::class.java, Pair(Constants.ZHUANTI_ID, adapter.getItem(position).id))
        }
    }

    override fun onSuccess(url: String, result: Any?) {
        val rows = GsonUtil.parseRows(result, ItemZhuanTiModel::class.java)
        rows.list?.let { dealRows(adapter, it) }
    }
}
