package pro.haichuang.learn.home.ui.activity.mine

import android.app.Activity
import android.content.Intent
import android.text.Editable
import android.text.TextWatcher
import com.jacy.kit.config.ContentView
import com.jacy.kit.config.gone
import com.jacy.kit.config.mStartActivity
import com.jacy.kit.config.show
import kotlinx.android.synthetic.main.activity_collect.*
import pro.haichuang.learn.home.R
import pro.haichuang.learn.home.adapter.CollectAdapter
import pro.haichuang.learn.home.bean.TabBean
import pro.haichuang.learn.home.config.BaseActivity
import pro.haichuang.learn.home.config.Constants
import pro.haichuang.learn.home.im.CollectAttachment
import pro.haichuang.learn.home.net.Url
import pro.haichuang.learn.home.ui.activity.find.FindDetailsActivity
import pro.haichuang.learn.home.ui.activity.index.HeightSchoolDetailsActivity
import pro.haichuang.learn.home.ui.activity.index.TeacherDetailsActivity
import pro.haichuang.learn.home.ui.activity.index.ZhuanTiDetailsActivity
import pro.haichuang.learn.home.ui.activity.index.itemmodel.CollegeModel
import pro.haichuang.learn.home.ui.activity.index.itemmodel.ItemTeacherModel
import pro.haichuang.learn.home.ui.activity.index.itemmodel.ItemZhuanTiModel
import pro.haichuang.learn.home.ui.activity.mine.itemmodel.CollectModel
import pro.haichuang.learn.home.ui.activity.news.NewsDetailsActivity
import pro.haichuang.learn.home.ui.fragment.itemview.ItemNews
import pro.haichuang.learn.home.utils.GsonUtil


@ContentView(R.layout.activity_collect)
class CollectActivity : BaseActivity() {
    private lateinit var tabBeans: ArrayList<TabBean>

    private val adapter by lazy { CollectAdapter(this) }

    private val isChat by lazy { intent.getBooleanExtra("isChat", false) }

    override fun initData() {
        listView.adapter = adapter
        pageUrl = Url.User.Collections
        post(Url.Publish.Channel)
        fetchPageData()
    }

    override fun onSuccess(url: String, result: Any?) {
        when (url) {
            Url.Publish.Channel -> {
                tabBeans = GsonUtil.parseArray(result, TabBean::class.java)
            }
            Url.User.Collections -> {
                GsonUtil.parseRows(result, CollectModel::class.java).list?.let {
                    dealRows(adapter, it)
                }
            }
        }
    }

    override fun initListener() {
        clear.setEdit(search_input)
        search_input.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (s.isNullOrEmpty()) {
                    clear.gone()
                    adapter.doSearch("")
                } else {
                    clear.show()
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })
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
                        tabBeans.find { it.path == content.channelPath }?.let{
                            mStartActivity(FindDetailsActivity::class.java, Pair(Constants.NEWS_ID, content.id))
                        }?:mStartActivity(NewsDetailsActivity::class.java, Pair(Constants.NEWS_ID, content.id))

                }
                2 -> {
                    val content = GsonUtil.parseObject(item.content, CollegeModel::class.java)
                    if (isChat) {

                    } else {
                        mStartActivity(HeightSchoolDetailsActivity::class.java, Pair(Constants.SCHOOL_ID, content.id))
                    }
                }
                3 -> {
                    val content = GsonUtil.parseObject(item.content, ItemTeacherModel::class.java)
                    if (isChat) {

                    } else
                        mStartActivity(TeacherDetailsActivity::class.java, Pair(Constants.TEACHER_ID, content.id))
                }
                4 -> {
                    val content = GsonUtil.parseObject(item.content, ItemZhuanTiModel::class.java)
                    if (isChat) {

                    } else {
                        mStartActivity(ZhuanTiDetailsActivity::class.java, Pair(Constants.ZHUANTI_ID, content.id))
                    }
                }
            }
        }
        search_btn.setOnClickListener {
            adapter.doSearch(search_input.text.toString())
        }
    }
}
