package galstyan.hayk.github.data

import javax.inject.Inject

// I know - I know... not the best name ever :D
class RepositoryRepository @Inject constructor(
    private val repositoryDataSource: RepositoryDataSource
) {

    suspend fun getRepositories() = repositoryDataSource.getRepositories()

    suspend fun getRepositoryDetails() = repositoryDataSource.getRepositoryDetails()
}