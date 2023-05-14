package galstyan.hayk.github.data

import galstyan.hayk.github.data.error.DataError
import galstyan.hayk.github.domain.error.AppError
import galstyan.hayk.github.domain.model.Repository
import javax.inject.Inject

// I know - I know... not the best name ever :D
class RepositoryRepository @Inject constructor(
    private val repositoryDataSource: RepositoryDataSource
) {

    suspend fun getRepositories(page: Int): List<Repository> = withErrorPropagation {
        repositoryDataSource.getRepositories(page)
    }

    suspend fun getRepositoryDetails(id: Long): Repository = withErrorPropagation {
        repositoryDataSource.getRepositoryDetails(id)
    }

    private inline fun <reified T> withErrorPropagation(block: () -> T): T {
        try {
            return block()
        } catch (e: Exception) {
            if (e is DataError) throw e.toDomain()
            else throw AppError.UnknownError
        }
    }
}