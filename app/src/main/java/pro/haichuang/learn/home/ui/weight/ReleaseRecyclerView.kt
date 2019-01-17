package pro.haichuang.learn.home.ui.weight

import android.content.Context
import android.support.v7.widget.GridLayoutManager
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
class ReleaseRecyclerView : SwipeMenuRecyclerView {
    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    init {
        layoutManager = GridLayoutManager(context, 3)
        addItemDecoration(DefaultItemDecoration(context.mgetColor(R.color.colorWhite),
                DensityUtil.dp2px(8f),
                DensityUtil.dp2px(8f)))
    }
}
