package pro.haichuang.learn.home.ui.activity.index

import pro.haichuang.learn.home.annotation.ContentView
import pro.haichuang.learn.home.config.BaseActivity
import pro.haichuang.learn.home.R


@ContentView(R.layout.activity_plan_compare)
class PlanCompareActivity : BaseActivity() {

    override fun initData() {
        titleModel.title = "计划对比"
    }
}
