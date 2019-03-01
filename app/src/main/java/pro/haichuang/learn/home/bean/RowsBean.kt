package pro.haichuang.learn.home.bean

class RowsBean<T> {

    var firstPage = false

    var firstResult = 0

    var lastPage = false

    var list: MutableList<T>? = null
}