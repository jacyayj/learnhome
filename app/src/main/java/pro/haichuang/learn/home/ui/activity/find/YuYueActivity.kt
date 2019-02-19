package pro.haichuang.learn.home.ui.activity.find

import android.app.Activity
import android.content.Intent
import com.luck.picture.lib.PictureSelector
import com.luck.picture.lib.config.PictureConfig
import kotlinx.android.synthetic.main.activity_yu_yue.*
import com.jacy.kit.config.ContentView
import pro.haichuang.learn.home.config.DataBindingActivity
import pro.haichuang.learn.home.ui.activity.find.viewmodel.YuYueModel
import pro.haichuang.learn.home.R
import pro.haichuang.learn.home.adapter.ReleaseImageAdapter
import pro.haichuang.learn.home.ui.dialog.PaymentDialog


@ContentView(R.layout.activity_yu_yue)
class YuYueActivity : DataBindingActivity<YuYueModel>() {
    private val adapter by lazy { ReleaseImageAdapter(this) }
    override fun initData() {
        titleModel.title = "预约填报"
        grid.adapter = adapter
    }

    override fun initListener() {
        yu_yue.setOnClickListener {
            PaymentDialog(this).show()
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                PictureConfig.CHOOSE_REQUEST -> {
                    PictureSelector.obtainMultipleResult(data).forEach {
                        adapter.insert(it.compressPath)
                    }
                }
            }
        }
    }
}
