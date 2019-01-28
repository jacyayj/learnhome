package pro.haichuang.learn.home.bean

import com.zhouyou.http.model.ApiResult

class Response<T> : ApiResult<T>() {

    private var message = ""

    override fun getMsg(): String {
        return message
    }

    override fun isOk(): Boolean {
        return code == 200
    }
}