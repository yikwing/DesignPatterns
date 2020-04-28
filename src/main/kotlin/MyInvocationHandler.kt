import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method
import java.lang.reflect.Proxy


fun main() {
    val myInvocationHandler = MyInvocationHandler(Hello())

    val newProxyInstance =
        Proxy.newProxyInstance(
            IHello::class.java.classLoader,
            arrayOf(IHello::class.java),
            myInvocationHandler
        ) as IHello

    newProxyInstance.hello("giao")
    newProxyInstance.say()

}

class MyInvocationHandler(val obj: Any) : InvocationHandler {
    override fun invoke(proxy: Any?, method: Method?, args: Array<out Any>?): Any? {
        println("before")
        val result = method!!.invoke(obj, *(args ?: emptyArray()))
        println("after\n")
        return result
    }
}

interface IHello {
    fun hello(str: String)
    fun say()
}

class Hello : IHello {
    override fun hello(str: String) {
        println("hello $str")
    }

    override fun say() {
        println("一给我里giaogiao")
    }
}