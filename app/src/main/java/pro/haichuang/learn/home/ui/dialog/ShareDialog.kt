package pro.haichuang.learn.home.ui.dialog

import android.app.Activity
import android.app.Dialog
import android.os.Bundle
import kotlinx.android.synthetic.main.dialog_share.*
import pro.haichuang.learn.home.R
import pro.haichuang.learn.home.utils.DialogUtils
import pro.haichuang.learn.home.utils.ShareUtils

class ShareDialog(private val context: Activity, private val title: String = "", private val url: String = "", private val content: String = "") : Dialog(context, R.style.Dialog) {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_share)
        DialogUtils.setBottom(window)
        share_to_friend.setOnClickListener {
            dismiss()
        }
        share_to_qq.setOnClickListener {
            ShareUtils.shareToQQ(context, title, url, if (content.length>50) content.substring(0,49) else content)
            dismiss()
        }
        share_to_wx.setOnClickListener {
            ShareUtils.shareToWx(context, title, url, if (content.length>50) content.substring(0,49) else content)
            dismiss()
        }
    }
}