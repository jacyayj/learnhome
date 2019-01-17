package pro.haichuang.learn.home.ui.weight

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.drawable.ColorDrawable
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.TextView
import com.jacy.kit.config.gone
import com.jacy.kit.config.mgetColor
import com.jacy.kit.config.mgetDimension
import com.jacy.kit.config.show
import pro.haichuang.learn.home.R

class SideBar : View {
    private val A_Z by lazy { arrayOf("定位", "热门", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z") }
    // 触摸事件
    private var onTouchingLetterChangedListener: OnTouchingLetterChangedListener? = null
    private var choose = -1// 选中
    private val paint = Paint()

    private var mTextDialog: TextView? = null

    /**
     * 为SideBar设置显示字母的TextView
     *
     * @param mTextDialog
     */
    fun setTextView(mTextDialog: TextView) {
        this.mTextDialog = mTextDialog
    }


    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context) : super(context)

    /**
     * 重写这个方法
     */
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        // 获取焦点改变背景颜色.
        val height = height// 获取对应高度
        val width = width // 获取对应宽度
        val singleHeight = height / A_Z.size - 2// 获取每一个字母的高度  (这里-2仅仅是为了好看而已)

        for (i in A_Z.indices) {
            paint.color = context.mgetColor(R.color.color009DFF)//设置字体颜色
//            paint.typeface = Typeface.DEFAULT_BOLD  //设置字体
            paint.isAntiAlias = true  //设置抗锯齿
            paint.textSize = context.mgetDimension(R.dimen.sp_10).toFloat()  //设置字母字体大小
            // 选中的状态
            if (i == choose) {
                paint.color = context.mgetColor(R.color.color009DFF)  //选中的字母改变颜色
//                paint.isFakeBoldText = true  //设置字体为粗体
            }
            // x坐标等于中间-字符串宽度的一半.
            val xPos = width / 2 - paint.measureText(A_Z[i]) / 2
            val yPos = (singleHeight * i + singleHeight).toFloat()
            canvas.drawText(A_Z[i], xPos, yPos, paint)  //绘制所有的字母
            paint.reset()// 重置画笔
        }

    }

    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        val action = event.action
        val y = event.y// 点击y坐标
        val oldChoose = choose
        val listener = onTouchingLetterChangedListener
        val c = (y / height * A_Z.size).toInt()// 点击y坐标所占总高度的比例*b数组的长度就等于点击b中的个数.

        when (action) {
            MotionEvent.ACTION_UP -> {
                setBackgroundDrawable(ColorDrawable(0x00000000))
                choose = -1//
                invalidate()
                mTextDialog?.gone()
            }

            else -> if (oldChoose != c) {  //判断选中字母是否发生改变
                if (c >= 0 && c < A_Z.size) {
                    listener?.onTouchingLetterChanged(A_Z[c])
                        mTextDialog?.text = A_Z[c]
                        mTextDialog?.show()

                    choose = c
                    invalidate()
                }
            }
        }
        return true
    }

    /**
     * 向外公开的方法
     *
     * @param onTouchingLetterChangedListener
     */
    fun setOnTouchingLetterChangedListener(
            onTouchingLetterChangedListener: OnTouchingLetterChangedListener) {
        this.onTouchingLetterChangedListener = onTouchingLetterChangedListener
    }

    /**
     * 接口
     *
     * @author coder
     */
    interface OnTouchingLetterChangedListener {
        fun onTouchingLetterChanged(s: String)
    }


}