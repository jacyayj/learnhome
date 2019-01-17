package pro.haichuang.learn.home.ui.weight

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import com.jacy.kit.adapter.CommonAdapter
import pro.haichuang.learn.home.R
import pro.haichuang.learn.home.bean.AreaBean

/**
 * Created by Administrator on 2016/9/22 0022.
 */
class LetterGridView : AutoGridView {
    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    var data : ArrayList<AreaBean>? = null
        set(value) {
            field = value
            value?.let {
                adapter = CommonAdapter(LayoutInflater.from(context), R.layout.item_area_grid,it)
            }
        }
}
