package pro.haichuang.learn.home.ui.dialog

import android.app.Activity
import android.app.Dialog
import android.os.Bundle
import kotlinx.android.synthetic.main.dialog_share.*
import pro.haichuang.learn.home.R
import pro.haichuang.learn.home.utils.DialogUtils
import pro.haichuang.learn.home.utils.ShareUtils
import java.io.File

class ShareDialog(private val context: Activity, private val title: String = "", private val url: String = "", private val content: String = "", private val picture: String = "") : Dialog(context, R.style.Dialog) {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_share)
        DialogUtils.setBottom(window)
        share_to_friend.setOnClickListener {
            dismiss()
        }
        share_to_qq.setOnClickListener {
            if (picture.isEmpty())
                ShareUtils.shareToQQ(context, title, url, if (content.length > 50) content.substring(0, 49) else content)
            else
                ShareUtils.sharePictureToQQFriend(context, File(picture))
            dismiss()
        }
        share_to_wx.setOnClickListener {
            if (picture.isEmpty())
                ShareUtils.shareToWx(context, title, url, if (content.length > 50) content.substring(0, 49) else content)
            else
                ShareUtils.sharePictureToWechatFriend(context, File(picture))
            dismiss()
        }
        share_to_moments.setOnClickListener {
            if (picture.isEmpty())
                ShareUtils.shareToMomments(context, title, url, if (content.length > 50) content.substring(0, 49) else content)
            else
                ShareUtils.sharePictureToWechatMomments(context, File(picture))
            dismiss()
        }
    }
}