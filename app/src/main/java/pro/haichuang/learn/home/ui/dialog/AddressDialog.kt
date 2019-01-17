package pro.haichuang.learn.home.ui.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import com.jacy.kit.adapter.CommonAdapter
import kotlinx.android.synthetic.main.dialog_choose_address.*
import pro.haichuang.learn.home.R
import pro.haichuang.learn.home.utils.DialogUtils

class AddressDialog(context: Context) : Dialog(context, R.style.Dialog) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_choose_address)
        setCanceledOnTouchOutside(true)
        DialogUtils.setBottom(window)
        close.setOnClickListener { dismiss() }
        grid.adapter = CommonAdapter(layoutInflater, R.layout.item_address, arrayListOf("全城", "新津县", "锦江区", "金牛区",
                "龙泉驿区", "武侯区", "青白江区", "新都区", "青羊区", "温江区", "成华区", "都江堰区", "彭州市", "邛崃市", "崇州市"))
    }
}