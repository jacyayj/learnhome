package pro.haichuang.learn.home.bean

class LetterBean {

    var letter = ""

    val areas by lazy { ArrayList<AreaBean>() }

    var grid = false

}