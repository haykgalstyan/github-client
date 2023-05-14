package galstyan.hayk.github.presentation.repositories.details

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.SavedStateHandle
import galstyan.hayk.github.domain.error.AppError
import galstyan.hayk.github.domain.model.Repository
import galstyan.hayk.github.domain.usecase.GetRepositoryDetails
import galstyan.hayk.github.presentation.error.UiError
import galstyan.hayk.github.threading.MainDispatcherRule
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test


class RepositoryDetailsViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()


    private val repository = Repository(
        id = 1L,
        name = "",
        description = "",
        stars = 0, forks = 0,
        language = null,
        link = ""
    )

    private val getRepositoryDetails = mockk<GetRepositoryDetails>()

    private val viewModel by lazy {
        RepositoryDetailsViewModel(
            SavedStateHandle(mapOf("repository_id" to repository.id)),
            getRepositoryDetails
        )
    }

    @Test
    fun `initially loaded repository on success`() = runTest {
        coEvery { getRepositoryDetails.invoke(repository.id) } returns repository
        assertEquals(
            RepositoryDetailsUiState.Data(repository),
            viewModel.uiStateObservable.value
        )
    }

    @Test
    fun `network error propagated to ui correctly`() = runTest {
        coEvery { getRepositoryDetails.invoke(repository.id) } throws AppError.ConnectionError
        assertEquals(
            RepositoryDetailsUiState.Error(UiError.ConnectionError),
            viewModel.uiStateObservable.value
        )
    }

    @Test
    fun `data error propagated to ui correctly`() = runTest {
        coEvery { getRepositoryDetails.invoke(repository.id) } throws AppError.DataError
        assertEquals(
            RepositoryDetailsUiState.Error(UiError.UnknownError),
            viewModel.uiStateObservable.value
        )
    }

    @Test
    fun `unknown error propagated to ui correctly`() = runTest {
        coEvery { getRepositoryDetails.invoke(repository.id) } throws AppError.UnknownError
        assertEquals(
            RepositoryDetailsUiState.Error(UiError.UnknownError),
            viewModel.uiStateObservable.value
        )
    }

    @Test
    fun `non app error propagated to ui correctly`() = runTest {
        coEvery { getRepositoryDetails.invoke(repository.id) } throws RuntimeException()
        assertEquals(
            RepositoryDetailsUiState.Error(UiError.UnknownError),
            viewModel.uiStateObservable.value
        )
    }
}