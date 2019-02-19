package pro.haichuang.learn.home.ui.fragment

import com.jacy.kit.adapter.CommonRecyclerAdapter
import com.jacy.kit.config.mStartActivity
import kotlinx.android.synthetic.main.fragment_message.*
import pro.haichuang.learn.home.R
import com.jacy.kit.config.ContentView
import pro.haichuang.learn.home.config.BaseFragment
import pro.haichuang.learn.home.ui.activity.message.FriendSearchActivity
import pro.haichuang.learn.home.ui.activity.message.MessageCenterActivity

@ContentView(R.layout.fragment_message)
class MessageFragment : BaseFragment() {


    override fun initData() {
        contact_view.adapter = CommonRecyclerAdapter(layoutInflater, R.layout.item_message, arrayListOf(1, 2, 3, 4, 5, 6))
    }

    override fun initListener() {
        to_message_center.setOnClickListener {
            mStartActivity(MessageCenterActivity::class.java)
        }
        to_search_message.setOnClickListener {
            mStartActivity(FriendSearchActivity::class.java)
        }
    }
}