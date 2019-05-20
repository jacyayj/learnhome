package pro.haichuang.learn.home.utils

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.widget.ScrollView
import com.vondear.rxtool.RxFileTool
import com.vondear.rxtool.RxImageTool
import com.vondear.rxtool.RxTool


object ScreenUtils {

    val path by lazy { RxFileTool.getDiskCacheDir(RxTool.getContext()) + "/share_img.png" }

    /**
     * http://blog.csdn.net/lyy1104/article/details/40048329
     */
    fun shotScrollView(scrollView: ScrollView) {
        var h = 0
        var bitmap: Bitmap
        for (i in 0 until scrollView.childCount) {
            h += scrollView.getChildAt(i).height
            scrollView.getChildAt(i).setBackgroundColor(Color.parseColor("#ffffff"))
        }
        bitmap = Bitmap.createBitmap(scrollView.width, h, Bitmap.Config.RGB_565)
        val canvas = Canvas(bitmap)
        scrollView.draw(canvas)
        RxImageTool.save(bitmap, path, Bitmap.CompressFormat.PNG)
    }

}