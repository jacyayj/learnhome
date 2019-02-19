package pro.haichuang.learn.home.ui.activity.index

import com.jacy.kit.adapter.CommonAdapter
import kotlinx.android.synthetic.main.activity_note_book.*
import com.jacy.kit.config.ContentView
import pro.haichuang.learn.home.config.BaseActivity
import pro.haichuang.learn.home.R


@ContentView(R.layout.activity_note_book)
class NoteBookActivity : BaseActivity() {

    override fun initData() {
        titleModel.title = "状元笔记"
        listView.adapter = CommonAdapter(layoutInflater, R.layout.item_note_book, arrayListOf("语文", "数学", "英语", "政治", "历史", "地理", "物理", "化学", "生物"))
    }
}
