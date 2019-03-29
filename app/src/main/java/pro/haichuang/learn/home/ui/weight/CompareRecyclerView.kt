package pro.haichuang.learn.home.ui.weight

import android.content.Context
import android.support.v7.widget.GridLayoutManager
import android.util.AttributeSet
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView

/**
 * Created by Administrator on 2016/9/22 0022.
 */
class CompareRecyclerView : SwipeMenuRecyclerView {
    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    fun setCount(count: Int) {
        layoutManager = GridLayoutManager(context, count, GridLayoutManager.VERTICAL, false)
    }
}
