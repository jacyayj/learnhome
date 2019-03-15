package pro.haichuang.learn.home.ui.activity.mine

import android.app.Activity
import android.content.Intent
import com.jacy.kit.config.ContentView
import com.jacy.kit.config.mStartActivityForResult
import kotlinx.android.synthetic.main.activity_file.*
import pro.haichuang.learn.home.R
import pro.haichuang.learn.home.config.DataBindingActivity
import pro.haichuang.learn.home.net.Url
import pro.haichuang.learn.home.ui.activity.CityListActivity
import pro.haichuang.learn.home.ui.activity.mine.viewmodel.FileModel
import pro.haichuang.learn.home.ui.dialog.AddressDialog
import pro.haichuang.learn.home.ui.dialog.ClassDialog


@ContentView(R.layout.activity_file)
class FileActivity : DataBindingActivity<FileModel>() {
    private val addressDialog by lazy {
        AddressDialog(this) {
            model.district = it
        }
    }
    private val classDialog by lazy {
        ClassDialog(this) {
            model.studentClass = it
        }
    }

    override fun initData() {
        titleModel.title = "个人档案"
        post(Url.User.FileGet, needSession = true)
    }

    override fun initListener() {
        choose_city.setOnClickListener { mStartActivityForResult(CityListActivity::class.java, 0x01) }
        choose_qx.setOnClickListener { addressDialog.show() }
        choose_class.setOnClickListener { classDialog.show() }
        save.setOnClickListener { autoPost(Url.User.FileSave) }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == 0x01) {
            model.city = data?.getStringExtra("city") ?: ""
        }
    }
}
