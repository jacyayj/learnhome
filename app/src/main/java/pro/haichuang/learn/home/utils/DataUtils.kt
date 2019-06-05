package pro.haichuang.learn.home.utils

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.jacy.kit.utils.DataCleanManager
import com.vondear.rxtool.RxTool
import pro.haichuang.learn.home.R
import pro.haichuang.learn.home.bean.IconLabel
import pro.haichuang.learn.home.bean.NameId
import pro.haichuang.learn.home.ui.fragment.itemview.ItemMine

object DataUtils {

    fun formatIndexGridData(): ArrayList<IconLabel> {
        val data = ArrayList<IconLabel>()
        data.add(IconLabel(R.drawable.icon_index_pdxt, "判断系统"))
        data.add(IconLabel(R.drawable.icon_index_mszx, "名师在线"))
        data.add(IconLabel(R.drawable.icon_index_vrkx, "VR看校"))
        data.add(IconLabel(R.drawable.icon_index_sjcx, "数据查询"))
        data.add(IconLabel(R.drawable.icon_index_hwlx, "海外留学"))
        data.add(IconLabel(R.drawable.icon_index_zzzs, "自主招生"))
        data.add(IconLabel(R.drawable.icon_index_zybj, "状元笔记"))
        data.add(IconLabel(R.drawable.icon_index_xlsy, "心理舒压"))
        data.add(IconLabel(R.drawable.icon_index_zxsp, "在线视频"))
        data.add(IconLabel(R.drawable.icon_index_gxdz, "高校单招"))
        return data
    }

    fun formatMineListData(): ArrayList<ItemMine> {
        val data = ArrayList<ItemMine>()
        data.add(ItemMine(R.drawable.icon_mine_wdye, "我的余额"))
        data.add(ItemMine(R.drawable.icon_mine_jrsq, "加入社群"))
        data.add(ItemMine(R.drawable.icon_mine_wdsc, "我的收藏"))
        data.add(ItemMine(R.drawable.icon_mine_wddd, "我的订单").apply { hasOrder = true })
        data.add(ItemMine(R.drawable.icon_mine_grda, "个人档案"))
        data.add(ItemMine(R.drawable.icon_mine_yqhy, "邀请好友"))
        data.add(ItemMine(R.drawable.icon_mine_lxwm, "联系我们", false))
        return data
    }

    fun formatMineSettingData(): ArrayList<ItemMine> {
        val data = ArrayList<ItemMine>()
        data.add(ItemMine("用户名", "张明Mutual"))
        data.add(ItemMine("支付密码", "设置"))
        data.add(ItemMine("修改密码"))
        data.add(ItemMine("清除缓存", DataCleanManager.getTotalCacheSize(RxTool.getContext()), canJump = false))
        data.add(ItemMine("意见反馈", line = true))
        data.add(ItemMine("常见问题"))
        data.add(ItemMine("使用指南"))
        data.add(ItemMine("关于我们"))
        return data
    }

    val countryData by lazy { formatCountryData() }

    val languageData by lazy { formatLanguageData() }

    val provinceData by lazy { formatProvinceData() }

    val piCiData by lazy { formatPiCiData() }

    val typeData by lazy { formatSchoolTypeData() }

    val levelData by lazy { formatSchoolLevelData() }

    val chuangBanData by lazy { formatChuangBanData() }

    fun findCountryByCode(code: String): String? {
        return countryData.find { it.code == code }?.name
    }

    fun findLanguageById(id: Int): String? {
        return languageData.find { it.id == id.toLong() }?.name
    }

    fun findProvinceById(id: Long): String? {
        return provinceData.find { it.id == id }?.name
    }

    fun findPiCiById(id: Int): String? {
        return piCiData.find { it.id == id.toLong() }?.name
    }

    fun findLevelById(id: Int): String? {
        return levelData.find { it.id == id.toLong() }?.name
    }

    fun findTypeById(id: Int): String? {
        return typeData.find { it.id == id.toLong() }?.name
    }

    fun findLevelByIds(ids: String): String {
        if (ids.isEmpty())
            return ""
        var names = ""
        ids.split(",").forEach { id ->
            names += "${levelData.find { it.id == id.toLong() }?.name},"
        }
        return names.removeSuffix(",")
    }

    fun findChuangBanById(id: Int): String? {
        return chuangBanData.find { it.id == id.toLong() }?.name
    }

    private fun formatCountryData(): ArrayList<NameId> {
        val data = ArrayList<NameId>()
        data.add(NameId("加拿大", "CA"))
        data.add(NameId("美国", "US"))
        data.add(NameId("英国", "GB"))
        data.add(NameId("马来西亚", "MY"))
        data.add(NameId("澳大利亚", "AU"))
        data.add(NameId("日本", "JP"))
        return data
    }

    private fun formatLanguageData(): ArrayList<NameId> {
        val data = ArrayList<NameId>()
        data.add(NameId("英语", 1))
        data.add(NameId("中英双语", 2))
        data.add(NameId("日语", 3))
        data.add(NameId("中日双语", 4))
        return data
    }

    private fun formatProvinceData(): ArrayList<NameId> {
        return Gson().fromJson<ArrayList<NameId>>(FileUtils.readProvince(RxTool.getContext()), object : TypeToken<ArrayList<NameId>>() {}.type)
    }

    private fun formatPiCiData(): ArrayList<NameId> {
        val data = ArrayList<NameId>()
        data.add(NameId("本科提前批次", 1))
        data.add(NameId("本科第一批次", 2))
        data.add(NameId("本科第二批次", 3))
        data.add(NameId("本科预科", 4))
        data.add(NameId("专科提前批次", 5))
        data.add(NameId("专科批次", 6))
        return data
    }

    private fun formatSchoolTypeData(): ArrayList<NameId> {
        val data = ArrayList<NameId>()
        data.add(NameId("综合类", 1))
        data.add(NameId("理工类", 2))
        data.add(NameId("师范类", 3))
        data.add(NameId("农林类", 4))
        data.add(NameId("政法类", 5))
        data.add(NameId("医药类", 6))
        data.add(NameId("财经类", 7))
        data.add(NameId("民族类", 8))
        data.add(NameId("语言类", 9))
        data.add(NameId("艺术类", 10))
        data.add(NameId("体育类", 11))
        data.add(NameId("军事类", 12))
        data.add(NameId("公安", 13))
        return data
    }

    private fun formatSchoolLevelData(): ArrayList<NameId> {
        val data = ArrayList<NameId>()
        data.add(NameId("211", 1))
        data.add(NameId("985", 2))
        data.add(NameId("示范高职", 3))
        data.add(NameId("骨干高职", 4))
        data.add(NameId("卓越工程师", 5))
        data.add(NameId("卓越医生", 6))
        data.add(NameId("卓越法律", 7))
        data.add(NameId("双一流高校", 8))
        data.add(NameId("双一流学科", 9))
        return data
    }

    private fun formatChuangBanData(): ArrayList<NameId> {
        val data = ArrayList<NameId>()
        data.add(NameId("公办", 1))
        data.add(NameId("民办", 2))
        data.add(NameId("中外合资", 3))
        return data
    }
}