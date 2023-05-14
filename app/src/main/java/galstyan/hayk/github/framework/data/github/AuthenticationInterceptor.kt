package galstyan.hayk.github.framework.data.github

import okhttp3.Interceptor

class AuthenticationInterceptor(private val token: String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain) = chain.proceed(
        chain.request().newBuilder()
            .addHeader("Authorization", "Bearer $token")
            .build()
    )
}