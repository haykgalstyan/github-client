package galstyan.hayk.github.framework

import galstyan.hayk.github.domain.model.Repository
import retrofit2.Call
import retrofit2.http.GET


interface GithubRepositoriesApi {

    @GET("repositories")
    fun getRepositories(): Call<List<Repository>>
}