package pro.haichuang.learn.home.ui.activity.index.itemmodel

class PiCiModel {

    var id = 0

    var enrollBatch = 0

    var province = 0L

    var score1 = 0

    var score2 = 0

    var fit = false

    val enrollBatchStr
        get() = when (enrollBatch) {
            2 -> "本一批"
            3 -> "本二批"
            4 -> "专科批"
            else -> ""
        }
}