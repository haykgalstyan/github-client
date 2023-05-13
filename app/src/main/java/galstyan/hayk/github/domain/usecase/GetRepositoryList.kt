package galstyan.hayk.github.domain.usecase

import galstyan.hayk.github.data.RepositoryRepository
import javax.inject.Inject

class GetRepositoryList @Inject constructor(
    private val repository: RepositoryRepository
) {
    suspend operator fun invoke() = repository.getRepositories()
}