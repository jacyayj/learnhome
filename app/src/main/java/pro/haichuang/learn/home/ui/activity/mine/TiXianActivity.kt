package pro.haichuang.learn.home.ui.activity.mine

import android.app.Activity
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import com.jacy.kit.config.ContentView
import com.jacy.kit.config.toast
import com.zhouyou.http.model.HttpParams
import kotlinx.android.synthetic.main.activity_ti_xian.*
import pro.haichuang.learn.home.R
import pro.haichuang.learn.home.config.BaseActivity
import pro.haichuang.learn.home.net.Url
import java.util.regex.Pattern


@ContentView(R.layout.activity_ti_xian)
class TiXianActivity : BaseActivity() {

    private val balance by lazy { intent.getStringExtra("balance") }

    override fun initData() {
        titleModel.title = "提现"
        balance_tv.text = "零钱余额¥${balance}元"
    }

    override fun initListener() {
        draw.setOnClickListener {
            post(Url.Account.Recharge, HttpParams("amount", amount.text.toString()), needSession = true)
        }
        amount.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (start > 0) {
                    val regex = "^\\d+.$"
                    val r = Pattern.compile(regex)
                    val matcher = r.matcher(s)
                    if (matcher.matches()) {
                        amount.filters = arrayOf(InputFilter.LengthFilter(s.length + 2))
                    }
                }
            }
        })
    }

    override fun onSuccess(url: String, result: Any?) {
        toast("提现申请成功，请关注账号余额")
        setResult(Activity.RESULT_OK)
        finish()
    }
}
