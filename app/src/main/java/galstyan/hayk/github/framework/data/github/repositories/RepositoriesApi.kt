package galstyan.hayk.github.framework.data.github.repositories

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface RepositoriesApi {

    @GET("search/repositories")
    fun getRepositories(
        @Query("q") query: String = "is:public",
        @Query("sort") sort: String = "stars",
        @Query("order") order: String = "desc",
        @Query("per_page") perPage: Int = 10,
        @Query("page") page: Int = 1,
    ): Call<ResponseDto>
}