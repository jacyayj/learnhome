package pro.haichuang.learn.home.ui.activity.index

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import kotlinx.android.synthetic.main.activity_video_play.*
import pro.haichuang.learn.home.R
import pro.haichuang.learn.home.config.Constants.VIDEO_URL


class VideoPlayActivity : AppCompatActivity() {

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_play)
        val ws = content.settings

        ws.javaScriptEnabled = true
        ws.allowFileAccess = true
        ws.databaseEnabled = true
        ws.domStorageEnabled = true
        ws.saveFormData = false
        ws.setSupportMultipleWindows(true)
        ws.allowContentAccess = true
        ws.setAppCacheEnabled(true)
        ws.cacheMode = WebSettings.LOAD_DEFAULT
        ws.loadWithOverviewMode = false//<==== 一定要设置为false，不然有声音没图像
        ws.useWideViewPort = true
        ws.setSupportZoom(true)
        ws.useWideViewPort = true
        content.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                return if (url?.startsWith("intent") == true || url?.startsWith("youku") == true) {
                    true
                } else {
                    super.shouldOverrideUrlLoading(view, url)
                }
            }
        }
        content.loadData("<iframe align='center' src=${intent.getStringExtra(VIDEO_URL)} frameborder='0' allowfullscreen='true'/>")
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
