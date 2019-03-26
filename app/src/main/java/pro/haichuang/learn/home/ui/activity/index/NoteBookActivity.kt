package pro.haichuang.learn.home.ui.activity.index

import android.support.design.widget.TabLayout
import com.jacy.kit.adapter.CommonAdapter
import com.jacy.kit.config.ContentView
import com.zhouyou.http.model.HttpParams
import kotlinx.android.synthetic.main.activity_note_book.*
import pro.haichuang.learn.home.R
import pro.haichuang.learn.home.config.BaseActivity
import pro.haichuang.learn.home.net.Url


@ContentView(R.layout.activity_note_book)
class NoteBookActivity : BaseActivity() {

    private var noteType = "笔记"

    override fun initData() {
        titleModel.title = "状元笔记"
        listView.adapter = CommonAdapter(layoutInflater, R.layout.item_note_book, arrayListOf("语文", "数学", "英语", "政治", "历史", "地理", "物理", "化学", "生物"))
        post(Url.Note.List, HttpParams().apply {
            put("noteType", "笔记")
        })
    }

    override fun initListener() {
        tab.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(p0: TabLayout.Tab?) {

            }

            override fun onTabUnselected(p0: TabLayout.Tab?) {
            }

            override fun onTabSelected(p0: TabLayout.Tab?) {
                noteType = p0?.text?.toString() ?: ""
            }
        })
    }
}
