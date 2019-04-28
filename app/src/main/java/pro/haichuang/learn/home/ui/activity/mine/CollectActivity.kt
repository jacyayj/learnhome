package pro.haichuang.learn.home.ui.activity.mine

import android.app.Activity
import android.content.Intent
import com.jacy.kit.config.ContentView
import com.jacy.kit.config.mStartActivity
import kotlinx.android.synthetic.main.activity_vr.*
import pro.haichuang.learn.home.R
import pro.haichuang.learn.home.adapter.CollectAdapter
import pro.haichuang.learn.home.config.BaseActivity
import pro.haichuang.learn.home.config.Constants
import pro.haichuang.learn.home.config.Constants.NEWS_ID
import pro.haichuang.learn.home.im.CollectAttachment
import pro.haichuang.learn.home.net.Url
import pro.haichuang.learn.home.ui.activity.find.FindDetailsActivity
import pro.haichuang.learn.home.ui.activity.index.HeightSchoolDetailsActivity
import pro.haichuang.learn.home.ui.activity.index.ZhuanTiDetailsActivity
import pro.haichuang.learn.home.ui.activity.index.itemmodel.CollegeModel
import pro.haichuang.learn.home.ui.activity.index.itemmodel.ItemZhuanTiModel
import pro.haichuang.learn.home.ui.activity.mine.itemmodel.CollectModel
import pro.haichuang.learn.home.ui.fragment.itemview.ItemNews
import pro.haichuang.learn.home.utils.GsonUtil


@ContentView(R.layout.activity_collect)
class CollectActivity : BaseActivity() {

    private val adapter by lazy { CollectAdapter(this) }

    private val isChat by lazy { intent.getBooleanExtra("isChat", false) }

    override fun initData() {
        listView.adapter = adapter
        pageUrl = Url.User.Collections
        fetchPageData()
    }

    override fun onSuccess(url: String, result: Any?) {
        when (url) {
            Url.User.Collections -> {
                GsonUtil.parseRows(result, CollectModel::class.java).list?.let {
                    dealRows(adapter, it)
                }
            }
        }
    }

    override fun initListener() {
        listView.setOnItemClickListener { _, _, position, _ ->
            val item = adapter.getItem(position)
            when (item.contentType) {
                1 -> {
                    val content = GsonUtil.parseObject(item.content, ItemNews::class.java)
                    if (isChat) {
                        val attachment = CollectAttachment()
                        attachment.header = ""
                        attachment.name = "大鹏"
                        attachment.title = content.title
                        attachment.image = content.picPath
                        attachment.text = content.description
                        setResult(Activity.RESULT_OK, Intent().putExtra("attachment", attachment))
                        finish()
                    } else
                        mStartActivity(FindDetailsActivity::class.java, Pair(NEWS_ID, content.id))
                }
                2->{
                    val content = GsonUtil.parseObject(item.content, CollegeModel::class.java)
                    if (isChat){

                    }else{
                        mStartActivity(HeightSchoolDetailsActivity::class.java, Pair(Constants.SCHOOL_ID, content.id))
                    }
                }
                4->{
                    val content = GsonUtil.parseObject(item.content, ItemZhuanTiModel::class.java)
                    if (isChat){

                    }else{
                        mStartActivity(ZhuanTiDetailsActivity::class.java, Pair(Constants.ZHUANTI_ID, content.id))
                    }
                }
            }
        }
    }
}
