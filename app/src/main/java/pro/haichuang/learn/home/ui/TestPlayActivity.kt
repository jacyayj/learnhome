package pro.haichuang.learn.home.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.webkit.WebSettings
import kotlinx.android.synthetic.main.activity_test_play.*
import pro.haichuang.learn.home.R

class TestPlayActivity : AppCompatActivity() {

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_play)
        val ws = content.settings
        content.loadData("<iframe src='http://player.youku.com/embed/XNDA3OTMzMTE4MA==' frameborder=0 'allowfullscreen'></iframe>")
        ws.javaScriptEnabled = true
        ws.allowFileAccess = true
        ws.databaseEnabled = true
        ws.domStorageEnabled = true
        ws.saveFormData = false
        ws.setAppCacheEnabled(true)
        ws.cacheMode = WebSettings.LOAD_DEFAULT
        ws.loadWithOverviewMode = false//<==== 一定要设置为false，不然有声音没图像
        ws.useWideViewPort = true
    }

    override fun onResume() {
        super.onResume()
        content.resumeTimers()
    }

    override fun onPause() {
        super.onPause()
        content.pauseTimers()
    }

    override fun onDestroy() {
        super.onDestroy()
        content.destroy()
    }
}
