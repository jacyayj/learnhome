package pro.haichuang.learn.home.ui.activity.index

import com.jacy.kit.adapter.CommonAdapter
import com.jacy.kit.config.ContentView
import com.jacy.kit.config.mStartActivity
import com.zhouyou.http.model.HttpParams
import kotlinx.android.synthetic.main.activity_note_book.*
import pro.haichuang.learn.home.R
import pro.haichuang.learn.home.config.BaseActivity
import pro.haichuang.learn.home.config.Constants.NOTE_ID
import pro.haichuang.learn.home.config.Constants.NOTE_SUBJECT
import pro.haichuang.learn.home.config.Constants.NOTE_TYPE
import pro.haichuang.learn.home.net.Url
import pro.haichuang.learn.home.ui.activity.index.itemmodel.NoteModel
import pro.haichuang.learn.home.utils.GsonUtil

@ContentView(R.layout.activity_note_book_child)
class NoteBookChildActivity : BaseActivity() {

    private val type by lazy { intent.getStringExtra(NOTE_TYPE) }
    private val subject by lazy { intent.getStringExtra(NOTE_SUBJECT) }
    private val adapter by lazy { CommonAdapter<NoteModel>(layoutInflater, R.layout.item_note_book_child) }

    override fun initData() {
        titleModel.title = subject
        listView.adapter = adapter
        pageUrl = Url.Note.List
        fetchPageData()
    }

    override fun initListener() {
        listView.setOnItemClickListener { _, _, position, _ ->
            mStartActivity(NoteBookDetailsActivity::class.java, Pair(NOTE_ID, adapter.getItem(position).id))
        }
    }

    override fun setPageParams(pageParams: HttpParams) {
        pageParams.put("noteType", type)
        pageParams.put("subject", subject)
    }

    override fun onSuccess(url: String, result: Any?) {
        GsonUtil.parseRows(result, NoteModel::class.java).list?.let { dealRows(adapter, it) }
    }
}
