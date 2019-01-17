package pro.haichuang.learn.home.utils

import pro.haichuang.learn.home.R
import pro.haichuang.learn.home.bean.IconLabel
import pro.haichuang.learn.home.ui.activity.mine.itemmodel.ItemFile
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
        data.add(ItemMine(R.drawable.icon_mine_wddd, "我的订单"))
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
        data.add(ItemMine("清除缓存", "1.2M", canJump = false))
        data.add(ItemMine("意见反馈", line = true))
        data.add(ItemMine("常见问题"))
        data.add(ItemMine("使用指南"))
        return data
    }

    fun formatFileData(): ArrayList<ItemFile> {
        val data = ArrayList<ItemFile>()
        data.add(ItemFile("考生姓名", "张德文"))
        data.add(ItemFile("毕业城市", "成都市", true))
        data.add(ItemFile("毕业区县", "武侯区", true))
        data.add(ItemFile("在读学校", "成都十二中"))
        data.add(ItemFile("学  籍  号", "20180254"))
        data.add(ItemFile("班        级", "12班", true))
        data.add(ItemFile("联系电话", "14785462158"))
        data.add(ItemFile("联 系 Q Q", "896658756"))
        data.add(ItemFile("联系邮箱", "896658756@qq.com"))
        return data
    }

    fun formatProvinceData(): ArrayList<String> {
        val data = ArrayList<String>()
        data.add("四川")
        data.add("重庆")
        data.add("北京")
        data.add("天津")
        data.add("上海")
        data.add("河北")
        data.add("山西")
        data.add("内蒙古")
        data.add("辽宁")
        data.add("吉林")
        data.add("黑龙江")
        data.add("江苏")
        data.add("浙江")
        data.add("安徽")
        data.add("福建")
        data.add("四川")
        data.add("重庆")
        data.add("北京")
        data.add("天津")
        data.add("上海")
        data.add("河北")
        data.add("山西")
        data.add("内蒙古")
        data.add("辽宁")
        data.add("吉林")
        data.add("黑龙江")
        data.add("江苏")
        data.add("浙江")
        data.add("安徽")
        data.add("福建")
        return data
    }

    fun formatSchoolRatingData(): ArrayList<String> {
        val data = ArrayList<String>()
        data.add("211")
        data.add("985")
        data.add("示范高职")
        data.add("骨干高职")
        data.add("卓越医生")
        data.add("卓越法律")
        data.add("双一流高校")
        data.add("双一流学科")
        return data
    }

    fun formatSchoolTypeData(): ArrayList<String> {
        val data = ArrayList<String>()
        data.add("综合")
        data.add("体育")
        data.add("民族")
        data.add("军事")
        data.add("公安")
        data.add("理工")
        data.add("财经")
        data.add("政法")
        data.add("师范")
        data.add("医药")
        data.add("农林")
        data.add("语言")
        data.add("艺术")
        return data
    }
    fun formatZuanYeData(): ArrayList<String> {
        val data = ArrayList<String>()
        data.add("哲学")
        data.add("经济学")
        data.add("法学")
        data.add("教育学")
        data.add("文学")
        data.add("历史学")
        data.add("理学")
        data.add("工学")
        data.add("农学")
        data.add("医学")
        data.add("管理学")
        data.add("其他")
        data.add("军事类")
        return data
    }

    fun formatLevelData(): ArrayList<String> {
        val data = ArrayList<String>()
        data.add("一本院校")
        data.add("二本院校")
        data.add("高职高专")
        return data
    }

}