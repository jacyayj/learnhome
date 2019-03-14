package pro.haichuang.learn.home.ui.activity.find

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import com.jacy.kit.adapter.CommonRecyclerAdapter
import com.jacy.kit.config.ContentView
import com.jacy.kit.config.toast
import com.luck.picture.lib.PictureSelector
import com.luck.picture.lib.config.PictureConfig
import com.scwang.smartrefresh.layout.util.DensityUtil
import com.yanzhenjie.recyclerview.swipe.widget.DefaultItemDecoration
import kotlinx.android.synthetic.main.activity_find_release.*
import pro.haichuang.learn.home.R
import pro.haichuang.learn.home.adapter.ReleaseImageAdapter
import pro.haichuang.learn.home.bean.TabBean
import pro.haichuang.learn.home.config.DataBindingActivity
import pro.haichuang.learn.home.net.Url
import pro.haichuang.learn.home.ui.activity.find.viewmodel.FindReleaseModel
import pro.haichuang.learn.home.utils.GsonUtil
import pro.haichuang.learn.home.utils.HttpUtils


@ContentView(R.layout.activity_find_release)
class FindReleaseActivity : DataBindingActivity<FindReleaseModel>() {
    private val adapter by lazy { ReleaseImageAdapter(this) }

    override fun initData() {
        grid.adapter = adapter
        post(Url.Publish.Channel)
    }

    override fun initListener() {
        release.setOnClickListener {
            model.picPaths = adapter.getPicPaths()
            autoPost(Url.Publish.Save, needSession = true)
        }
    }

    override fun onSuccess(url: String, result: Any?) {
        when (url) {
            Url.Publish.Save -> {
                toast("发布成功")
                finish()
            }
            Url.Publish.Channel -> {
                val tabs = GsonUtil.parseArray(result, TabBean::class.java)
                tab.addItemDecoration(DefaultItemDecoration(Color.TRANSPARENT, DensityUtil.dp2px(10f), 0))
                tab.adapter = CommonRecyclerAdapter(layoutInflater, R.layout.item_tab_channel, tabs) { v, t, i ->
                    if (i == 0) {
                        model.id = t.id
                        tabs[i].checked = true
                    }
                    v.setOnClickListener {
                        if (!t.checked) {
                            model.id = t.id
                            tabs.forEach {
                                it.checked = it.id == t.id
                            }
                        }
                    }
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                PictureConfig.CHOOSE_REQUEST -> {
                    PictureSelector.obtainMultipleResult(data).forEach { m ->
                        adapter.insert(m.compressPath)
                        HttpUtils.upLoadFile(m.compressPath) {
                            adapter.insertUpload(m.compressPath, it)
                        }
                    }
                }
            }
        }
    }
}
