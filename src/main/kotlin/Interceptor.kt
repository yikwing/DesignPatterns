interface Interceptor {

    fun intercept(chain: Chain): Response

    interface Chain {
        fun requests(): Requests
        fun proceed(requests: Requests): Response
        fun connect(): Connection
    }

}


class RealInterceptorChain(val interceptors: List<Interceptor>, val index: Int, val requests: Requests) :
    Interceptor.Chain {

    override fun requests(): Requests {
        return requests
    }

    override fun proceed(requests: Requests): Response {

        val next = RealInterceptorChain(interceptors, index + 1, requests)
        val interceptor = interceptors[index]
        val response = interceptor.intercept(next)

        return response
    }


    override fun connect(): Connection {
        return Connection()
    }
}


class InterceptorOne : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val rc1 = chain as RealInterceptorChain
        println("拦截器1 ${rc1.index}")

        val requests = rc1.requests
        val response = rc1.proceed(requests)
        println("拦截器--响应返回1")

        return response
    }
}

class InterceptorTwo : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val rc1 = chain as RealInterceptorChain
        println("拦截器2 ${rc1.index}")

        val requests = rc1.requests
        val response = rc1.proceed(requests)
        println("拦截器--响应返回2")

        return response
    }
}

class InterceptorThree : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val rc1 = chain as RealInterceptorChain
        println("拦截器3 ${rc1.index}")

        val requests = rc1.requests
        val response = rc1.proceed(requests)
        println("拦截器--响应返回3")

        return response
    }
}

class LastInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        return Response()
    }
}


fun main() {
    val interceptors = ArrayList<Interceptor>()
    interceptors.add(InterceptorOne())
    interceptors.add(InterceptorTwo())
    interceptors.add(InterceptorThree())
    interceptors.add(LastInterceptor())

    val requests = Requests()
    val realInterceptorChain = RealInterceptorChain(interceptors, 0, requests)

    realInterceptorChain.proceed(requests)

}


class Response
class Requests
class Connection