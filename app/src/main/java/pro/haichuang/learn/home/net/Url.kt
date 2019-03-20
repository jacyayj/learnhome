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
     * 用户相关接口
     */
    object User {
        private const val base = "user/"

        const val Login = "${base}login"

        const val Register = "${base}register"

        const val Info = "${base}info"

        const val FileSave = "${base}file/save"

        const val FileGet = "${base}file/get"
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
    }

    /**
     * 专题讲座相关接口
     */
    object Lecture {
        private const val base = "lecture/"

        const val Apply = "${base}apply"

        const val List = "${base}list"

        const val Get = "${base}get"
    }

    /**
     * 院校相关接口
     */
    object VR {
        private const val base = "college/vr/"

        const val List = "${base}list"
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

    object Account {
        private const val base = "account/"

        const val Fee = "${base}fee"

        const val Order = "${base}vip/order"
    }
}