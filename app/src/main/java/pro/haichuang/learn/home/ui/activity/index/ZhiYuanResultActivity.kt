package pro.haichuang.learn.home.ui.activity.index

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.view.View
import com.jacy.kit.adapter.CommonAdapter
import com.jacy.kit.config.ContentView
import com.jacy.kit.config.gone
import com.jacy.kit.config.show
import com.jacy.kit.config.toast
import com.vondear.rxtool.RxFileTool
import com.vondear.rxtool.RxImageTool
import com.vondear.rxtool.RxTimeTool
import com.zhouyou.http.model.HttpParams
import kotlinx.android.synthetic.main.activity_zhiyuan_result.*
import kotlinx.android.synthetic.main.item_zhiyuan_result.*
import kotlinx.android.synthetic.main.item_zhiyuan_result.view.*
import org.json.JSONArray
import org.json.JSONObject
import pro.haichuang.learn.home.R
import pro.haichuang.learn.home.config.BaseActivity
import pro.haichuang.learn.home.config.Constants.JUDGE_BATCH_STR
import pro.haichuang.learn.home.config.Constants.JUDGE_SUBJECT
import pro.haichuang.learn.home.net.Url
import pro.haichuang.learn.home.ui.activity.index.itemmodel.CollegeModel
import pro.haichuang.learn.home.ui.activity.index.viewmodel.ResultModel
import pro.haichuang.learn.home.ui.dialog.ChooseZhiYuanPopup
import pro.haichuang.learn.home.ui.dialog.ShareDialog
import pro.haichuang.learn.home.utils.GsonUtil
import pro.haichuang.learn.home.utils.ScreenUtils


@ContentView(R.layout.activity_zhiyuan_result)
class ZhiYuanResultActivity : BaseActivity() {

    private val subject by lazy { intent.getStringExtra(JUDGE_SUBJECT) }
    private val batch by lazy { intent.getStringExtra(JUDGE_BATCH_STR) }
    private val volunteerText by lazy { intent.getStringExtra("volunteerText") }
    private val params by lazy { intent.getSerializableExtra("params") as HttpParams }
    private lateinit var data: ArrayList<CollegeModel>
    private val zhiyuanList by lazy { ArrayList<String>() }
    private val adapter by lazy {
        CommonAdapter<CollegeModel>(layoutInflater, R.layout.item_zhiyuan_result) { v, t, _ ->
            t.majors?.let {
                v.child_listView.adapter = CommonAdapter(layoutInflater, R.layout.item_zhiyuan_result_major, it)
            }
            v.choose_zhiyuan.setOnClickListener {
                showSortDialog(it, t)
            }
        }
    }

    override fun initData() {
        post(Url.Judge.Get, needSession = true)
        listView.adapter = adapter
    }

    @SuppressLint("SetTextI18n")
    override fun onSuccess(url: String, result: Any?) {
        when (url) {
            Url.Judge.Save -> {
                toast("修改成功")
            }
            Url.Judge.Get -> {
                GsonUtil.parseObject(result, ResultModel::class.java).let {
                    label_1.text = "四川 ${RxTimeTool.getCurrentDateTime("yyyy")}年 ${batch}【${it.sn} 志愿表】"
                    label_2.text = "您的成绩:${it.score}  $subject:线差${it.difference}  您的位次:${it.rank}"
                    it.volunteers?.let {
                        data = it
                        zhiyuanList.clear()
                        it.forEach {
                            zhiyuanList.add(it.zhiyuanStr)
                        }
                        adapter.refresh(data)
                    }
                }
            }
        }
    }

    private fun showSortDialog(view: View, item: CollegeModel) {
        ChooseZhiYuanPopup(view, zhiyuanList) { priority ->
            if (item.priority != priority) {
                post(Url.Judge.Priority, HttpParams("oldPriority", item.priority.toString()).apply {
                    put("newPriority", priority.toString())
                }, needSession = true) {
                    post(Url.Judge.Get, needSession = true)
                }
            }
        }.show()
    }

    override fun initListener() {
        abey_group.setOnCheckedChangeListener { _, checkedId ->
            val array = JSONArray(volunteerText)
            for (i in 0 until array.length()) {
                val obj = array[i]
                if (obj is JSONObject) {
                    if (checkedId == R.id.agree) {
                        if (obj.getInt("id") == agree.tag) {
                            obj.put("isObey", true)
                        }
                    } else {
                        if (obj.getInt("id") == disagree.tag) {
                            obj.put("isObey", false)
                        }
                    }
                }
            }
            params.put("volunteerText", array.toString())
            post(Url.Judge.Save, params, needSession = true)
        }
        to_save.setOnClickListener {
            if (::data.isInitialized) {
                data.forEach {
                    it.onShot = true
                }
                qr_code_view.show()
                qr_code_view.postDelayed({
                    RxImageTool.save(ScreenUtils.shotScrollView(root), RxFileTool.getSDCardPath() + "test.jpg", Bitmap.CompressFormat.JPEG)
                }, 100)
                toast("保存成功")
                root.postDelayed({
                    data.forEach {
                        it.onShot = false
                    }
                    qr_code_view.gone()
                }, 100)
            }
        }
        to_share.setOnClickListener {
            ShareDialog(this).show()
        }
    }
}
