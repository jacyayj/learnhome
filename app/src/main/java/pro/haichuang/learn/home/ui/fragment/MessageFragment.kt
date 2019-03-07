package pro.haichuang.learn.home.ui.fragment

import com.jacy.kit.adapter.CommonAdapter
import com.jacy.kit.config.ContentView
import com.jacy.kit.config.mStartActivity
import com.netease.nim.uikit.api.NimUIKit
import kotlinx.android.synthetic.main.fragment_message.*
import pro.haichuang.learn.home.R
import pro.haichuang.learn.home.config.BaseFragment
import pro.haichuang.learn.home.ui.activity.message.FriendSearchActivity
import pro.haichuang.learn.home.ui.activity.message.MessageCenterActivity

@ContentView(R.layout.fragment_message)
class MessageFragment : BaseFragment() {

    private val adapter by lazy { CommonAdapter(layoutInflater, R.layout.item_message, arrayListOf(1, 2, 3, 4, 5, 6)) }

    override fun initData() {
        contact_view.adapter = adapter
    }

    override fun initListener() {
        to_message_center.setOnClickListener {
            mStartActivity(MessageCenterActivity::class.java)
        }
        to_search_message.setOnClickListener {
            mStartActivity(FriendSearchActivity::class.java)
        }
        contact_view.setOnItemClickListener { _, _, position, _ ->
            NimUIKit.startP2PSession(context, "im_test002")
        }
    }
}