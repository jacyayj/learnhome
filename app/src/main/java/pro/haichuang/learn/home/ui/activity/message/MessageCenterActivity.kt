package pro.haichuang.learn.home.ui.activity.message

import com.jacy.kit.adapter.CommonAdapter
import kotlinx.android.synthetic.main.activity_message_center.*
import pro.haichuang.learn.home.R
import com.jacy.kit.config.ContentView
import pro.haichuang.learn.home.config.BaseActivity
import pro.haichuang.learn.home.ui.activity.message.itemmodel.MessageCenterModel


@ContentView(R.layout.activity_message_center)
class MessageCenterActivity : BaseActivity() {

    override fun initData() {
        titleModel.title = "消息中心"
        listView.adapter = CommonAdapter(layoutInflater,R.layout.item_message_center, arrayListOf(MessageCenterModel(0),MessageCenterModel(1),MessageCenterModel(2),MessageCenterModel(2)))
    }

}
