package pro.haichuang.learn.home.ui.weight

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.ListView

class MListView:ListView {

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    private var xDistance = 0f
    private var yDistance = 0f
    private var xLast = 0f
    private var yLast = 0f

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        when (ev.action) {
            MotionEvent.ACTION_DOWN -> {
                yDistance = 0f
                xDistance = yDistance
                xLast = ev.x
                yLast = ev.y
            }
            MotionEvent.ACTION_MOVE -> {
                val curX = ev.x
                val curY = ev.y

                xDistance += Math.abs(curX - xLast)
                yDistance += Math.abs(curY - yLast)
                xLast = curX
                yLast = curY

                /**
                 * X轴滑动距离大于Y轴滑动距离，也就是用户横向滑动时，返回false，ScrollView不处理这次事件，
                 * 让子控件中的TouchEvent去处理，所以横向滑动的事件交由子控件处理， ScrollView只处理纵向滑动事件
                 */
                if (xDistance > yDistance) {
                    return false
                }
            }
        }

        return super.onInterceptTouchEvent(ev)
    }
}