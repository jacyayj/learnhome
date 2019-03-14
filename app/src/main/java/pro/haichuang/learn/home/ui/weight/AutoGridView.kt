package pro.haichuang.learn.home.ui.weight

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.GridView
import com.jacy.kit.adapter.CommonAdapter
import pro.haichuang.learn.home.R
import pro.haichuang.learn.home.bean.ImageBean

/**
 * Created by Administrator on 2016/9/22 0022.
 */
open class AutoGridView : GridView {
    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val expandSpec = View.MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE shr 2, View.MeasureSpec.AT_MOST)
        super.onMeasure(widthMeasureSpec, expandSpec)
    }

    var imageData: ArrayList<ImageBean>? = null
        set(value) {
            if (!value.isNullOrEmpty())
                adapter = CommonAdapter(LayoutInflater.from(context), R.layout.item_square_image, value)
        }
}
