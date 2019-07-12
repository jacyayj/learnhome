package pro.haichuang.learn.home.ui.dialog

import android.app.Activity
import android.app.Dialog
import android.os.Bundle
import kotlinx.android.synthetic.main.dialog_invate.*
import pro.haichuang.learn.home.R
import pro.haichuang.learn.home.utils.DialogUtils
import pro.haichuang.learn.home.utils.ShareUtils

class InvateDialog(private val activity: Activity) : Dialog(activity, R.style.Dialog) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_invate)
        DialogUtils.initDialogWidth(window, 0.8f)
        close.setOnClickListener { dismiss() }
        share_to_qq.setOnClickListener {
            ShareUtils.shareToQQ(activity, "升学之家", "http://www.baidu.com", "升学之家")
            dismiss()
        }
        share_to_wx.setOnClickListener {
            ShareUtils.shareToWx(activity, "升学之家", "http://www.baidu.com", "升学之家")
            dismiss()
        }
        share_to_moments.setOnClickListener {
            ShareUtils.shareToMomments(activity, "升学之家", "http://www.baidu.com", "升学之家")
            dismiss()
        }
    }

}