package pro.haichuang.learn.home.ui.activity.mine

import com.jacy.kit.config.ContentView
import pro.haichuang.learn.home.R
import pro.haichuang.learn.home.config.BaseActivity
import pro.haichuang.learn.home.net.Url

@ContentView(R.layout.activity_order_teacher)
class OrderTeacherActivity : BaseActivity() {

    override fun initData() {
        pageUrl = Url.Order.TeacherList
        fetchPageData()
    }

}
