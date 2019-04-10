package pro.haichuang.learn.home.adapter

import android.widget.LinearLayout
import com.jacy.kit.config.gone
import com.netease.nim.uikit.business.session.viewholder.MsgViewHolderBase
import com.netease.nim.uikit.common.ui.recyclerview.adapter.BaseMultiItemFetchLoadAdapter
import com.netease.nim.uikit.common.util.sys.ScreenUtil
import kotlinx.android.synthetic.main.item_chat_collect.view.*
import pro.haichuang.learn.home.R
import pro.haichuang.learn.home.im.CollectAttachment

class CollectViewHolder(adapter: BaseMultiItemFetchLoadAdapter<*, *>?) : MsgViewHolderBase(adapter) {
    override fun getContentResId() = R.layout.item_chat_collect

    override fun inflateContentView() {
        setLayoutParams((ScreenUtil.screenWidth * 0.5).toInt(), LinearLayout.LayoutParams.WRAP_CONTENT, view.contentView)
    }

    override fun bindContentView() {
        val attachment = message.attachment
        if (attachment is CollectAttachment) {
            view.name.text = attachment.name
            view.title.text = attachment.title
            view.text.text = attachment.text
            if (attachment.image.isEmpty())
                view.image.gone()
            else
                view.text.gone()
        }
    }

    override fun rightBackground() = R.drawable.nim_message_right_white_bg

    override fun leftBackground() = R.drawable.nim_message_right_white_bg
}