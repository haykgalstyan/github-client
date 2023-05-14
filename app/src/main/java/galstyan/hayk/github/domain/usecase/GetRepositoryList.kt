package galstyan.hayk.github.domain.usecase

import galstyan.hayk.github.data.RepositoryRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetRepositoryList @Inject constructor(
    private val repository: RepositoryRepository,
    private val ioDispatcher: CoroutineDispatcher,
) {

    suspend operator fun invoke(page: Int) = withContext(ioDispatcher) { repository.getRepositories(page) }
}