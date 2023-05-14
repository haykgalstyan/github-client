package galstyan.hayk.github.framework.data.github.repositories

import com.google.gson.annotations.SerializedName
import galstyan.hayk.github.domain.model.Repository

data class RepositoryDto(
    @SerializedName("id")
    val id: Long,
    @SerializedName("name")
    val name: String,
    @SerializedName("description")
    val description: String?,
    @SerializedName("stargazers_count")
    val stars: Int,
    @SerializedName("forks_count")
    val forks: Int,
    @SerializedName("language")
    val language: String?,
    @SerializedName("html_url")
    val htmlUrl: String,
) {
    fun toDomainModel() = Repository(
        id = id,
        name = name,
        description = description,
        stars = stars,
        forks = forks,
        language = language,
        link = htmlUrl
    )
}