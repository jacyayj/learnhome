package pro.haichuang.learn.home.ui.activity

import android.app.Activity
import android.content.Intent
import com.jacy.kit.config.ContentView
import kotlinx.android.synthetic.main.activity_city_list.*
import pro.haichuang.learn.home.R
import pro.haichuang.learn.home.adapter.CityListAdapter
import pro.haichuang.learn.home.config.BaseActivity
import pro.haichuang.learn.home.ui.weight.SideBar


@ContentView(R.layout.activity_city_list)
class CityListActivity : BaseActivity() {

    private val adapter by lazy { CityListAdapter(this) }

    override fun initData() {
        listView.adapter = adapter
        slide.setTextView(text_dialog)
        adapter.result = {
            setResult(Activity.RESULT_OK, Intent().putExtra("city", it))
            finish()
        }
    }

    override fun initListener() {
        slide.setOnTouchingLetterChangedListener(object : SideBar.OnTouchingLetterChangedListener {
            override fun onTouchingLetterChanged(s: String) {
                listView.setSelection(adapter.getChoosePosition(s))
            }
        })
    }
}
