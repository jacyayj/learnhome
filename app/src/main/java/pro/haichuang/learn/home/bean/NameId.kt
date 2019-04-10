package pro.haichuang.learn.home.bean

class NameId {

    constructor(name: String, id: Long) {
        this.id = id
        this.name = name
    }

    constructor(name: String, code: String) {
        this.code = code
        this.name = name
    }

    var id = 0L
    var name = ""
    var code = ""
    var v = ""
    var child: ArrayList<NameId>? = null
}