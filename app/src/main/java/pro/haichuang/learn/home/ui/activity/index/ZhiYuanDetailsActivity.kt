package pro.haichuang.learn.home.ui.activity.index

import android.content.Intent
import android.support.v4.app.FragmentPagerAdapter
import android.view.View
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.jacy.kit.config.ContentView
import com.jacy.kit.config.toast
import com.zhouyou.http.model.HttpParams
import kotlinx.android.synthetic.main.activity_zhiyuan_details.*
import pro.haichuang.learn.home.R
import pro.haichuang.learn.home.config.BaseActivity
import pro.haichuang.learn.home.config.Constants
import pro.haichuang.learn.home.config.Constants.JUDGE_BATCH_STR
import pro.haichuang.learn.home.config.Constants.JUDGE_SUBJECT
import pro.haichuang.learn.home.net.Url
import pro.haichuang.learn.home.ui.dialog.TitleNoticeDialog
import pro.haichuang.learn.home.ui.dialog.ZhiYuanResultDialog
import pro.haichuang.learn.home.ui.fragment.index.ZhiYuanCollegeFragment
import pro.haichuang.learn.home.ui.fragment.index.ZhiYuanMajorFragment
import pro.haichuang.learn.home.ui.fragment.index.ZhiYuanSelfFragment


@ContentView(R.layout.activity_zhiyuan_details)
class ZhiYuanDetailsActivity : BaseActivity() {

    private val fs by lazy {
        arrayListOf(
                ZhiYuanCollegeFragment.getInstance(subject, mScore, batch),
                ZhiYuanMajorFragment.getInstance(subject, mScore, batch),
                ZhiYuanSelfFragment.getInstance(subject, mScore, batch)
        )
    }
    private val titles by lazy { arrayListOf("院校优先填报", "专业优先填报", "自主填报") }

    private val mScore by lazy { intent.getIntExtra(Constants.JUDGE_SCORE, -1) }
    private val batch by lazy { intent.getIntExtra(Constants.JUDGE_BATCH, -1) }
    private val batchStr by lazy { intent.getStringExtra(JUDGE_BATCH_STR) }
    private val subject by lazy { intent.getIntExtra(JUDGE_SUBJECT, -1) }
    private val isDifference by lazy { intent.getBooleanExtra(Constants.JUDGE_IS_DIFFERENCE, false) }

    private val zhiyuanDialog by lazy {
        ZhiYuanResultDialog(this, { createResult() }) { id ->
            fs[tab.selectedTabPosition].reset(id)
        }
    }
    private val volunteerText = JsonArray()
    private val httpParams by lazy {
        HttpParams("batch", batch.toString()).apply {
            put("score", mScore.toString())
            put("subject", subject.toString())
        }
    }

    override fun initData() {
        if (isDifference)
            titleModel.title = "${if (subject == 1) "文科" else "理科"}   线差:$mScore    $batchStr"
        else
            titleModel.title = "成绩:${mScore}分 ${if (subject == 1) "文科" else "理科"}   $batchStr"
        pager.offscreenPageLimit = 3
        pager.adapter = object : FragmentPagerAdapter(supportFragmentManager) {
            override fun getItem(p0: Int) = fs[p0]
            override fun getPageTitle(position: Int) = titles[position]
            override fun getCount() = fs.size
        }
        tab.setupWithViewPager(pager)
    }

    fun choose(v:View){
        fs[tab.selectedTabPosition].choose(v)
    }

    override fun onSuccess(url: String, result: Any?) {
        when (url) {
            Url.Judge.Save -> {
                toast("志愿生成成功！")
                startActivity(Intent(this, ZhiYuanResultActivity::class.java)
                        .putExtra(JUDGE_BATCH_STR, batchStr)
                        .putExtra("params", httpParams)
                        .putExtra("volunteerText", volunteerText.toString())
                        .putExtra(JUDGE_SUBJECT, if (subject == 1) "本科" else "专科"))
            }
        }
    }

    override fun initListener() {
        create_zhiyuan.setOnClickListener {
            createResult()
        }
        show_result.setOnClickListener {
            val chooseData = fs[tab.selectedTabPosition].getChooseData()
            if (chooseData.isEmpty())
                toast("请先选择志愿")
            else
                zhiyuanDialog.show(chooseData)
        }
    }

    private fun createResult() {
        val chooseData = fs[tab.selectedTabPosition].getChooseData()
        if (chooseData.size < 3) {
            TitleNoticeDialog(this).show()
        } else {
            val prioritys = ArrayList<Int>()
            chooseData.forEach {
                val json = JsonObject()
                if (it.majorIds.isEmpty()) {
                    toast("请选择院校专业")
                    return
                }
                json.addProperty("collegeId", it.id)
                json.addProperty("priority", it.priority)
                json.addProperty("isObey", it.obey)
                json.addProperty("majorIds", it.majorIds)
                volunteerText.add(json)
            }
            prioritys.sort()
            if (!isOrderNumber(prioritys)) {
                toast("选择无效，请选择连续的志愿")
                return
            }
            httpParams.put("volunteerText", volunteerText.toString())
            post(Url.Judge.Save, httpParams, needSession = true)
        }
    }

    /**
     * 是否是连续数字
     *
     * @param numOrStr
     * @return
     */
    private fun isOrderNumber(arr: ArrayList<Int>): Boolean {
        for (i in 0..arr.lastIndex) {
            if (i != arr.lastIndex && arr[i] + 1 != arr[i + 1])
                return false
        }
        return true
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        fs[tab.selectedTabPosition].onActivityResult(requestCode, resultCode, data)
    }
}
