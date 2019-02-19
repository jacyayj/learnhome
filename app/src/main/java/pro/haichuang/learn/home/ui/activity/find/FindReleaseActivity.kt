package pro.haichuang.learn.home.ui.activity.find

import android.app.Activity
import android.content.Intent
import com.luck.picture.lib.PictureSelector
import com.luck.picture.lib.config.PictureConfig
import kotlinx.android.synthetic.main.activity_find_release.*
import pro.haichuang.learn.home.R
import pro.haichuang.learn.home.adapter.ReleaseImageAdapter
import com.jacy.kit.config.ContentView
import pro.haichuang.learn.home.config.DataBindingActivity
import pro.haichuang.learn.home.ui.activity.find.viewmodel.FindReleaseModel


@ContentView(R.layout.activity_find_release)
class FindReleaseActivity : DataBindingActivity<FindReleaseModel>() {

    private val adapter by lazy { ReleaseImageAdapter(this) }

    override fun initData() {
        grid.adapter = adapter
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
