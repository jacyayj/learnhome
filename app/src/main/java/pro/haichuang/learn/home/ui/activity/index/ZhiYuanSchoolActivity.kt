package pro.haichuang.learn.home.ui.activity.index

import com.jacy.kit.config.ContentView
import pro.haichuang.learn.home.config.BaseActivity
import pro.haichuang.learn.home.R
import pro.haichuang.learn.home.ui.dialog.ZhiYuanSchoolDialog


@ContentView(R.layout.activity_zhiyuan_school)
class ZhiYuanSchoolActivity : BaseActivity() {

    override fun initData() {
        titleModel.title = "历年统计"
        titleModel.titleRightIcon = R.drawable.icon_notice
        titleModel.showRight = true
        titleModel.onRightClick={
            ZhiYuanSchoolDialog(this).show()
        }
    }

}
