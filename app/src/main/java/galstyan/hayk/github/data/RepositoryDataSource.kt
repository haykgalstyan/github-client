package galstyan.hayk.github.data

import galstyan.hayk.github.domain.model.Repository


interface RepositoryDataSource {

    suspend fun getRepositories(page: Int): List<Repository>

    suspend fun getRepositoryDetails(id: Long): Repository
}