package pro.haichuang.learn.home.config

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.layout_title.*
import pro.haichuang.learn.home.BR
import pro.haichuang.learn.home.R
import pro.haichuang.learn.home.annotation.ContentView

/**
 * Created by jacy on 2018/7/24.
 * 根activity，初始化各种通用数据；
 */
abstract class BaseActivity : AppCompatActivity() {

    internal val titleModel by lazy { TitleModel() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())
        initDatabinding()
        initTitle()
        initData()
        initListener()
    }

    /**
     * 设置页面layout
     */
    internal fun getLayoutId(): Int {
        if (javaClass.isAnnotationPresent(ContentView::class.java)) {
            val field = javaClass.getAnnotation(ContentView::class.java)
            return field.layoutId
        } else {
            throw NullPointerException("activity 未设置页面layoutId")
        }
    }

    /**
     * 初始化数据
     */
    open fun initData() {}

    /**
     * 初始化监听器
     */
    open fun initListener() {}

    /**
     * 初始化databinding
     */
    open fun initDatabinding() {

    }

    /**
     * 初始化标题
     */
    open fun initTitle() {
        title_layout?.let {
            titleModel.onLeftClick = {
                onBackPressed()
            }
            val b = DataBindingUtil.bind<ViewDataBinding>(it, DataBindingUtil.getDefaultComponent())
            b?.setVariable(com.android.databinding.library.baseAdapters.BR.title, titleModel)
        }
    }

    fun back(view: View) {
        finish()
    }

}
