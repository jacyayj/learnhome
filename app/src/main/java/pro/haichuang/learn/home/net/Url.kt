package pro.haichuang.learn.home.net

object Url {

    const val app_key = "s3o8KTWUsN25JnuIE97T6zHcIo6BOdOw"

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
}