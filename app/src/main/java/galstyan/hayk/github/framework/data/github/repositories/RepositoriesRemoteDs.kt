package galstyan.hayk.github.framework.data.github.repositories

import galstyan.hayk.github.data.RepositoryDataSource
import galstyan.hayk.github.data.error.DataError
import galstyan.hayk.github.domain.model.Repository
import java.io.IOException
import javax.inject.Inject


class RepositoriesRemoteDs @Inject constructor(
    private val api: RepositoriesApi,
) : RepositoryDataSource {

    private val cache = HashMap<Long, Repository>()

    // not doing page data cache yet cause there's no use-case of getting already loaded page in UI
    // otherwise it would be a good idea


    override suspend fun getRepositories(page: Int): List<Repository> {
        try {
            val response = api.getRepositories(perPage = PER_PAGE, page = page).execute()

            if (!response.isSuccessful) throw DataError.UnknownError
            val dtoList = response.body()?.repositories ?: throw DataError.NotFoundError

            return dtoList.map { dto -> dto.toDomainModel().also { cache[it.id] = it } }
        } catch (e: Exception) {
            throw when (e) {
                is DataError -> e
                is IOException -> DataError.ConnectionError
                else -> DataError.UnknownError
            }
        }
    }

    override suspend fun getRepositoryDetails(id: Long): Repository {
        return cache[id] ?: throw DataError.NotFoundError
    }


    companion object {
        // todo: move to a config object higher up
        private const val PER_PAGE = 50
    }
}