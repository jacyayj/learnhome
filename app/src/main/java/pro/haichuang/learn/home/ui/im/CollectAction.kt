package pro.haichuang.learn.home.ui.im

import com.jacy.kit.config.mStartActivity
import com.netease.nim.uikit.business.session.actions.BaseAction
import pro.haichuang.learn.home.R
import pro.haichuang.learn.home.ui.activity.mine.CollectActivity

class CollectAction : BaseAction(R.drawable.shouc, R.string.collect) {


    override fun onClick() {
        activity.mStartActivity(CollectActivity::class.java)
    }
}