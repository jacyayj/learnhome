package pro.haichuang.learn.home.ui.activity.index

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.design.widget.TabLayout
import com.jacy.kit.config.*
import kotlinx.android.synthetic.main.activity_zhiyuan.*
import pro.haichuang.learn.home.BR
import pro.haichuang.learn.home.R
import pro.haichuang.learn.home.bean.TabBean
import pro.haichuang.learn.home.config.BaseActivity
import pro.haichuang.learn.home.config.Constants.JUDGE_BATCH
import pro.haichuang.learn.home.config.Constants.JUDGE_IS_DIFFERENCE
import pro.haichuang.learn.home.config.Constants.JUDGE_PROVINCE
import pro.haichuang.learn.home.config.Constants.JUDGE_SCORE
import pro.haichuang.learn.home.config.Constants.JUDGE_SUBJECT
import pro.haichuang.learn.home.ui.dialog.ChoosePiCiPopup
import pro.haichuang.learn.home.ui.dialog.GridMultiplePopup
import pro.haichuang.learn.home.ui.dialog.NoticePopup


@ContentView(R.layout.activity_zhiyuan)
class ZhiYuanActivity : BaseActivity() {

    private val tabBeans by lazy { arrayListOf(TabBean("预估总分"), TabBean("线差", true)) }
    private val provincePopup by lazy {
        GridMultiplePopup(choose_province) { it, name ->
            province = it
            choose_province.text = name
        }.apply { setOnDismissListener { choose_province.isChecked = false } }
    }

    private var batch = 1

    private var subject = 1

    private var province = ""

    override fun initData() {
        initTab()
    }

    override fun initListener() {
        subject_group.setOnCheckedChangeListener { _, checkedId ->
            subject = if (checkedId == R.id.like) 1 else 2
        }
        to_result.setOnClickListener {
            if (input.text.isEmpty())
                toast("请输入" + if (tab.selectedTabPosition == 0) "高考分数" else "线分差")
            else
                mStartActivity(ZhiYuanPiCiActivity::class.java,
                        Pair(JUDGE_SCORE, input.text.toString().toInt()),
                        Pair(JUDGE_BATCH, batch),
                        Pair(JUDGE_PROVINCE, province),
                        Pair(JUDGE_IS_DIFFERENCE, tab.selectedTabPosition == 1),
                        Pair(JUDGE_SUBJECT, subject))
        }
        choose_province.setOnClickListener {
            provincePopup.show(2)
        }
        spinner.setOnClickListener {
            ChoosePiCiPopup(spinner) { name, batch ->
                spinner.text = name
                this.batch = batch
            }.show()
        }
        tab.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(p0: TabLayout.Tab?) {
                if (p0?.position == 1) {
                    NoticePopup(tab).show()
                }
            }

            override fun onTabUnselected(p0: TabLayout.Tab?) {
            }

            override fun onTabSelected(p0: TabLayout.Tab?) {
                when (p0?.position) {
                    0 -> {
                        input.hint = "高考分数"
                        spinner.gone()
                    }
                    1 -> {
                        input.hint = "线分差"
                        spinner.show()
                    }
                }
            }
        })
    }

    private fun initTab() {
        for (bean in tabBeans) {
            val binding = DataBindingUtil.inflate<ViewDataBinding>(layoutInflater, R.layout.item_tab_notice, tab, false)
            binding.setVariable(BR.item, bean)
            binding.executePendingBindings()
            tab.addTab(tab.newTab().setCustomView(binding.root))
        }
    }
}
