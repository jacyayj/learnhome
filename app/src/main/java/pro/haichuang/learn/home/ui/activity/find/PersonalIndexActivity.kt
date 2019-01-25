package pro.haichuang.learn.home.ui.activity.find

import com.jacy.kit.adapter.CommonAdapter
import com.jacy.kit.config.mStartActivity
import kotlinx.android.synthetic.main.activity_personal_index.*
import pro.haichuang.learn.home.annotation.ContentView
import pro.haichuang.learn.home.config.DataBindingActivity
import pro.haichuang.learn.home.ui.activity.find.viewmodel.PersonalIndexModel
import pro.haichuang.learn.home.R


@ContentView(R.layout.activity_personal_index)
class PersonalIndexActivity : DataBindingActivity<PersonalIndexModel>() {

    override fun initData() {
        listView.adapter = CommonAdapter(layoutInflater, R.layout.item_personal_index, arrayListOf(1, 2, 5, 3, 3, 5, 5))
    }

    override fun initListener() {
        listView.setOnItemClickListener { parent, view, position, id ->
            mStartActivity(FindDetailsActivity::class.java)
        }
    }
}



