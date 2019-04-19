package pro.haichuang.learn.home.utils

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.widget.ScrollView



object ScreenUtils {

    /**
     * http://blog.csdn.net/lyy1104/article/details/40048329
     */
    fun shotScrollView(scrollView: ScrollView): Bitmap? {
        var h = 0
        var bitmap: Bitmap? = null
        for (i in 0 until scrollView.childCount) {
            h += scrollView.getChildAt(i).height
            scrollView.getChildAt(i).setBackgroundColor(Color.parseColor("#ffffff"))
        }
        bitmap = Bitmap.createBitmap(scrollView.width, h, Bitmap.Config.RGB_565)
        val canvas = Canvas(bitmap)
        scrollView.draw(canvas)
        return bitmap
    }

}