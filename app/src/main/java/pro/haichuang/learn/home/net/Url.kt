package pro.haichuang.learn.home.net

object Url {

    const val app_key = "s3o8KTWUsN25JnuIE97T6zHcIo6BOdOw"
    const val base_url = "http://118.24.80.29:8080/learn-home-server/api/app/"

    object News{

        private const val base = "news/"

        const val List = "${base}list"

    }

    object Sms{

        private const val base = "sms/"

        const val Send = "${base}send"

    }

    object User{
        private const val base = "user/"

        const val Login ="${base}login"

        const val Register ="${base}register"
    }
    object Teacher{
        private const val base = "teacher/"

        const val Get ="${base}get"

        const val List ="${base}list"
    }
}