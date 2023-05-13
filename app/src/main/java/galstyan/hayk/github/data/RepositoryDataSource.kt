package galstyan.hayk.github.data

import galstyan.hayk.github.domain.model.Repository


interface RepositoryDataSource {

    suspend fun getRepositories(): List<Repository>

    suspend fun getRepositoryDetails(): Repository
}