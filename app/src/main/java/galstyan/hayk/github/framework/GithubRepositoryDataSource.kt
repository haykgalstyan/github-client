package galstyan.hayk.github.framework

import com.google.gson.annotations.SerializedName
import galstyan.hayk.github.data.RepositoryDataSource
import galstyan.hayk.github.domain.model.Repository
import javax.inject.Inject

class GithubRepositoryDataSource @Inject constructor(
    private val githubRepositoriesApi: GithubRepositoriesApi,
) : RepositoryDataSource {

    override suspend fun getRepositories(): List<Repository> {
        val response = githubRepositoriesApi.getRepositories().execute()
        return listOf()
    }

    override suspend fun getRepositoryDetails(): Repository {
        TODO("Not yet implemented")
    }

    // List: name, description, stars.
    // Dtls: name, description, stars, forks, primary lang

    data class RepositoryDto(
        @SerializedName("name")
        val name: String,
        @SerializedName("description")
        val description: String,
        @SerializedName("stargazers_count")
        val stars: Int,
        @SerializedName("forks_count")
        val forks: Int,
        @SerializedName("language")
        val primaryLanguage: String?
    )
}