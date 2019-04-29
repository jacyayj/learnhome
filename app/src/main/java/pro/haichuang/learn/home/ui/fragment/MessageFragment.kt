package pro.haichuang.learn.home.ui.fragment

import com.jacy.kit.config.ContentView
import com.jacy.kit.config.mStartActivity
import com.netease.nim.uikit.api.NimUIKit
import kotlinx.android.synthetic.main.fragment_message.*
import pro.haichuang.learn.home.R
import pro.haichuang.learn.home.config.BaseFragment
import pro.haichuang.learn.home.ui.activity.message.FriendSearchActivity
import pro.haichuang.learn.home.ui.activity.message.MessageCenterActivity
import pro.haichuang.learn.home.ui.activity.message.MyUpActivity
import pro.haichuang.learn.home.ui.activity.mine.MyCommentActivity

@ContentView(R.layout.fragment_message)
class MessageFragment : BaseFragment() {

    override fun initData() {
    }

    override fun initListener() {
        to_comment.setOnClickListener {
            mStartActivity(MyCommentActivity::class.java)
        }
        to_up.setOnClickListener {
            mStartActivity(MyUpActivity::class.java)
        }
        to_message_center.setOnClickListener {
            mStartActivity(MessageCenterActivity::class.java)
        }
        to_search_message.setOnClickListener {
            mStartActivity(FriendSearchActivity::class.java)
        }
    }
}