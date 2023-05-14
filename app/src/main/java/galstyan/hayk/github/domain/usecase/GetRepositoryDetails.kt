package galstyan.hayk.github.domain.usecase

import galstyan.hayk.github.data.RepositoryRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetRepositoryDetails @Inject constructor(
    private val repository: RepositoryRepository,
    private val ioDispatcher: CoroutineDispatcher,
) {
    suspend operator fun invoke(repositoryId: Long) = withContext(ioDispatcher) {
        repository.getRepositoryDetails(repositoryId)
    }
}