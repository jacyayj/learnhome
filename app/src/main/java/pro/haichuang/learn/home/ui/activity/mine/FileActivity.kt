package pro.haichuang.learn.home.ui.activity.mine

import android.app.Activity
import android.content.Intent
import android.widget.RadioGroup
import com.jacy.kit.config.ContentView
import com.jacy.kit.config.mStartActivityForResult
import com.jacy.kit.config.toast
import kotlinx.android.synthetic.main.activity_file.*
import pro.haichuang.learn.home.R
import pro.haichuang.learn.home.bean.AreaBean
import pro.haichuang.learn.home.config.DataBindingActivity
import pro.haichuang.learn.home.net.Url
import pro.haichuang.learn.home.ui.activity.CityListActivity
import pro.haichuang.learn.home.ui.activity.mine.viewmodel.FileModel
import pro.haichuang.learn.home.ui.dialog.AddressDialog
import pro.haichuang.learn.home.ui.dialog.ClassDialog
import pro.haichuang.learn.home.utils.GsonUtil


@ContentView(R.layout.activity_file)
class FileActivity : DataBindingActivity<FileModel>(), RadioGroup.OnCheckedChangeListener {


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

    override fun onSuccess(url: String, result: Any?) {
        when (url) {
            Url.User.FileGet -> {
                notifyModel(GsonUtil.parseObject(result, FileModel::class.java))
                model.innerChecked(false)
            }
            Url.User.FileSave -> {
                model.done = true
                toast("保存成功")
            }
        }
    }

    override fun initListener() {
        year_group.setOnCheckedChangeListener(this)
        vip_group.setOnCheckedChangeListener(this)
        subject_group.setOnCheckedChangeListener(this)
        choose_city.setOnClickListener { mStartActivityForResult(CityListActivity::class.java, 0x01) }
        choose_qx.setOnClickListener {
            if (model.city.isEmpty())
                toast("请先选择毕业城市")
            else
                addressDialog.show(model.districtData)
        }
        choose_class.setOnClickListener { classDialog.show() }
        save.setOnClickListener { autoPost(Url.User.FileSave, needSession = true) }
    }

    override fun onCheckedChanged(group: RadioGroup?, checkedId: Int) {
        when (checkedId) {
            R.id.year_btn_1 -> model.graduate = 1
            R.id.year_btn_2 -> model.graduate = 2
            R.id.year_btn_3 -> model.graduate = 3
            R.id.vip_btn_1 -> model.type = 1
            R.id.vip_btn_2 -> model.type = 2
            R.id.subject_btn_1 -> model.subject = 1
            R.id.subject_btn_2 -> model.subject = 2
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == 0x01) {
            (data?.getSerializableExtra("area") as AreaBean).let {
                model.city = it.city_name
                model.district = ""
                model.districtData = it.child
            }
        }
    }
}
