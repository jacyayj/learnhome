package pro.haichuang.learn.home.annotation

import java.lang.annotation.Inherited

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION)
@MustBeDocumented
@Inherited
annotation class Result(val name: String)