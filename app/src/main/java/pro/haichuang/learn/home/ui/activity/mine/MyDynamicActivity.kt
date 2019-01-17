package pro.haichuang.learn.home.ui.activity.mine

import com.jacy.kit.adapter.CommonAdapter
import kotlinx.android.synthetic.main.activity_my_dynamic.*
import kotlinx.android.synthetic.main.item_mine_index.view.*
import pro.haichuang.learn.home.R
import pro.haichuang.learn.home.annotation.ContentView
import pro.haichuang.learn.home.config.BaseActivity


@ContentView(R.layout.activity_my_dynamic)
class MyDynamicActivity : BaseActivity() {
    private val adapter by lazy {
        CommonAdapter(layoutInflater, R.layout.item_mine_index, arrayListOf(1, 2, 3, 4, 5, 6, 7, 8, 9)) { v, _, i->
            v.delete.setOnClickListener {
                remove(i)
            }
        }
    }

    override fun initData() {
        listView.adapter = adapter
    }

    private fun remove(position: Int) {
        adapter.remove(position)
    }
}
