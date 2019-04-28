package pro.haichuang.learn.home.net

object Url {

    const val app_key = "s3o8KTWUsN25JnuIE97T6zHcIo6BOdOw"
    const val base_url = "http://118.24.80.29:8080/learn-home-server/api/app/"
    const val image_base_url = "http://118.24.80.29:8080/learn-home-server"

    /**
     * 验证码相关接口
     */
    object Sms {

        private const val base = "sms/"

        const val Send = "${base}send"
    }


    /**
     * 判断相关接口
     */
    object Judge {

        private const val base = "judge/"

        const val Score = "${base}score"

        const val College = "${base}college"

        const val Save = "${base}volunteer/save"

        const val Get = "${base}volunteer/get"

        const val Priority = "${base}volunteer/priority"
    }


    /**
     * 验证码相关接口
     */
    object Fqa {

        private const val base = "fqa/"

        const val Get = "${base}get"

        const val List = "${base}list"
    }

    /**
     * 广告相关接口
     */
    object Ad {

        private const val base = "ad/"

        const val List = "${base}list"
    }


    /**
     * 验证码相关接口
     */
    object Major {

        private const val base = "major/"

        const val Category = "${base}category"

        const val List = "${base}list"

        const val Get = "${base}get"

        const val College = "${base}college"

        const val Sort = "${base}sort"

    }

    /**
     * 海外留学相关接口
     */
    object ForeignCollege {

        private const val base = "foreign_college/"

        const val Type = "${base}type"

        const val List = "${base}list"

        const val Get = "${base}get"
    }


    /**
     * 状元笔记相关接口
     */
    object Note {

        private const val base = "note/"

        const val List = "${base}list"

        const val Get = "${base}get"
    }

    /**
     * 高校单招相关接口
     */
    object HeightSchool {

        private const val base = "gxdz/"

        const val List = "${base}list"

        const val Get = "${base}get"
    }

    /**
     * 自主招生相关接口
     */
    object ZhaoSheng {

        private const val base = "zzzs/"

        const val List = "${base}list"

        const val Get = "${base}get"
    }

    /**
     * 在线视频相关接口
     */
    object Video {

        private const val base = "video/"

        const val List = "${base}list"
    }

    /**
     * 用户相关接口
     */
    object User {
        private const val base = "user/"

        const val Account = "${base}account"

        const val Login = "${base}login"

        const val Register = "${base}register"

        const val ForgetPassword = "${base}forgetPassword"

        const val PayPassword = "${base}payPassword"

        const val Info = "${base}info"

        const val UpdateInfo = "${base}info/update"

        const val FileSave = "${base}file/save"

        const val FileGet = "${base}file/get"

        const val Collections = "${base}collections"

        const val ThirdLogin = "${base}thirdLogin"

        const val ThirdBind = "${base}thirdBind"
    }

    /**
     * 好友相关接口
     */
    object Friend {
        private const val base = "friend/"

        const val Attention = "${base}attention"

        const val MyAttention = "${base}attention/my"

        const val MyFans = "${base}fans/my"

        const val Recommend = "${base}recommend"

        const val Search = "${base}search"
    }

    /**
     * 名师相关接口
     */
    object Teacher {
        private const val base = "teacher/"

        const val Get = "${base}get"

        const val List = "${base}list"

        const val Fee = "${base}fee"

        const val Order = "${base}order"

        const val Collect = "${base}collect"

        const val CommentSave = "${base}comment/save"

        const val CommentList = "${base}comment/list"
    }

    /**
     * 资讯相关接口
     */
    object News {
        private const val base = "news/"

        const val Get = "${base}get"

        const val List = "${base}list"

        const val Channel = "${base}channel"
    }

    /**
     * 文章相关接口
     */
    object Content {
        private const val base = "content/"

        const val Collect = "${base}collect"

        const val Up = "${base}up"

        const val Delete = "${base}delete"

        const val My = "${base}my"

        const val Get = "${base}get"

        const val Recommend = "${base}recommend"
    }

    /**
     * 专题讲座相关接口
     */
    object Lecture {
        private const val base = "lecture/"

        const val Apply = "${base}apply"

        const val List = "${base}list"

        const val Get = "${base}get"

        const val Collect = "${base}collect"
    }

    /**
     * 院校相关接口
     */
    object College {
        private const val base = "college/"

        const val VrList = "${base}vr/list"

        const val List = "${base}list"

        const val Get = "${base}get"

        const val Collect = "${base}collect"

        const val Compare = "${base}compare"

        const val EnrollList = "${base}enroll/college"

        const val EnrollMajor = "${base}enroll/major"
    }

    /**
     * 发现相关接口
     */
    object Publish {
        private const val base = "publish/"

        const val Get = "${base}get"

        const val List = "${base}list"

        const val Channel = "${base}channel"

        const val Save = "${base}save"
    }

    /**
     * 上传相关接口
     */
    object Upload {
        private const val base = "upload/"

        const val Upload = "${base}upload"
    }

    /**
     * 评论相关接口
     */
    object Comment {
        private const val base = "comment/"

        const val My = "${base}my"

        const val List = "${base}list"

        const val Save = "${base}save"

        const val Up = "${base}up"
    }

    /**
     * 用户账户相关接口
     */
    object Account {
        private const val base = "account/"

        const val Fee = "${base}fee"

        const val Recharge = "${base}recharge"

        const val Detail = "${base}detail"

        const val Activate = "${base}vip/activate"
    }

    /**
     * 订单相关接口
     */
    object Order {

        private const val base = "order/"

        const val Accept = "${base}accept"

        const val Cancel = "${base}cancel"

        const val Finish = "${base}finish"

        const val Get = "${base}get"

        const val Pay = "${base}pay"

        const val Refund = "${base}refund"

        const val MemberList = "${base}list/member"

        const val TeacherList = "${base}list/teacher"

        const val Collect = "${base}collect"
    }
}