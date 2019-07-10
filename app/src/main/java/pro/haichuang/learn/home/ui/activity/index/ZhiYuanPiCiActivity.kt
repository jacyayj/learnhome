package pro.haichuang.learn.home.ui.activity.index

import android.annotation.SuppressLint
import android.view.View
import com.jacy.kit.adapter.CommonAdapter
import com.jacy.kit.config.ContentView
import com.jacy.kit.config.mStartActivity
import com.zhouyou.http.model.HttpParams
import kotlinx.android.synthetic.main.activity_zhiyuan_pici.*
import pro.haichuang.learn.home.R
import pro.haichuang.learn.home.config.BaseActivity
import pro.haichuang.learn.home.config.Constants
import pro.haichuang.learn.home.config.Constants.JUDGE_BATCH
import pro.haichuang.learn.home.config.Constants.JUDGE_BATCH_STR
import pro.haichuang.learn.home.config.Constants.JUDGE_IS_DIFFERENCE
import pro.haichuang.learn.home.config.Constants.JUDGE_PROVINCE
import pro.haichuang.learn.home.config.Constants.JUDGE_SUBJECT
import pro.haichuang.learn.home.net.Url
import pro.haichuang.learn.home.ui.activity.index.itemmodel.PiCiModel
import pro.haichuang.learn.home.utils.GsonUtil


@ContentView(R.layout.activity_zhiyuan_pici)
class ZhiYuanPiCiActivity : BaseActivity() {

    private val mScore by lazy { intent.getIntExtra(Constants.JUDGE_SCORE, -1) }
    private val batch by lazy { intent.getIntExtra(JUDGE_BATCH, -1) }
    private val subject by lazy { intent.getIntExtra(JUDGE_SUBJECT, -1) }
    private val province by lazy { intent.getStringExtra(JUDGE_PROVINCE) }
    private val isDifference by lazy { intent.getBooleanExtra(JUDGE_IS_DIFFERENCE, false) }

    override fun initData() {
        titleModel.title = "选择批次"
        post(Url.Judge.Score, HttpParams("province", province))
    }

    @SuppressLint("SetTextI18n")
    override fun onSuccess(url: String, result: Any?) {
        GsonUtil.parseArray(result, PiCiModel::class.java).let { data ->
            val score = if (isDifference) data.find { it.enrollBatch == batch }?.let { if (subject == 1) it.score1 + mScore else it.score2 + mScore }
                    ?: 0
            else mScore
            data.asReversed().forEachIndexed { i, it ->
                when (subject) {
                    1 -> {
                        if (score >= it.score1) {
                            it.fit = true
                            pici.text = " ${it.enrollBatchStr}"
                            if (i == 0)
                                data[1].fit = true
                            if (i == 0 || i == 1)
                                data[2].fit = true
                        }
                    }
                    2 -> {
                        if (score >= it.score2) {
                            it.fit = true
                            pici.text = " ${it.enrollBatchStr}"
                            if (i == 0)
                                data[1].fit = true
                            if (i == 0 || i == 1)
                                data[2].fit = true
                        }
                    }
                }
            }
            listView.adapter = CommonAdapter(layoutInflater, R.layout.item_pici, data)
        }
    }

    fun toDetails(v: View?) {
        val pici = v?.tag
        if (pici is PiCiModel) {
            mStartActivity(ZhiYuanDetailsActivity::class.java,
                    Pair(Constants.JUDGE_SCORE, mScore),
                    Pair(JUDGE_BATCH, pici.enrollBatch),
                    Pair(JUDGE_BATCH_STR, pici.enrollBatchStr),
                    Pair(JUDGE_IS_DIFFERENCE, isDifference),
                    Pair(JUDGE_SUBJECT, subject))
        }
    }
}
