package galstyan.hayk.github.framework

import com.google.gson.Gson
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object GithubClientFactory {

    private const val BASE_URL = "https://api.github.com/"

    private val httpClient = OkHttpClient()
        .newBuilder()
        .build()

    private val gson = Gson()
    private val gsonConverter = GsonConverterFactory.create(gson)

    private val githubClient = Retrofit.Builder()
        .client(httpClient)
        .baseUrl(BASE_URL)
        .addConverterFactory(gsonConverter)
        .build()

    fun repositoriesClient() = githubClient.create(GithubRepositoriesApi::class.java)
}