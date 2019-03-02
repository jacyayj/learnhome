package pro.haichuang.learn.home.ui.weight

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.util.AttributeSet
import com.jacy.kit.config.mgetColor
import com.jacy.kit.config.mgetDimension
import com.scwang.smartrefresh.layout.util.DensityUtil
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView
import com.yanzhenjie.recyclerview.swipe.widget.DefaultItemDecoration
import pro.haichuang.learn.home.R

/**
 * Created by Administrator on 2016/9/22 0022.
 */
class VerticalRecyclerView : SwipeMenuRecyclerView {
    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    init {
        layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        addItemDecoration(DefaultItemDecoration(context.mgetColor(R.color.colorF0F7FA), 0, context.mgetDimension(R.dimen.divider_size)))
    }

    fun setDivider(color:Int,height:Float){
        addItemDecoration(DefaultItemDecoration(context.mgetColor(color), 0, DensityUtil.dp2px(height)))
    }

}
