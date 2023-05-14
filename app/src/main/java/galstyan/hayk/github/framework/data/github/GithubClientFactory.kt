package galstyan.hayk.github.framework.data.github

import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class GithubClientFactory(accessToken: String, logsEnabled: Boolean) {
    private val authInterceptor = AuthenticationInterceptor(accessToken)

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level =
            if (logsEnabled) HttpLoggingInterceptor.Level.BODY
            else HttpLoggingInterceptor.Level.NONE
    }

    private val httpClient = OkHttpClient()
        .newBuilder()
        .addInterceptor(authInterceptor)
        .addInterceptor(loggingInterceptor)
        .build()

    private val gson = Gson()
    private val gsonConverter = GsonConverterFactory.create(gson)

    private val githubClient = Retrofit.Builder()
        .client(httpClient)
        .baseUrl(BASE_URL)
        .addConverterFactory(gsonConverter)
        .build()

    fun <T> createClient(clientClass: Class<T>): T = githubClient.create(clientClass)

    companion object {
        private const val BASE_URL = "https://api.github.com/"
    }
}