package galstyan.hayk.github.presentation.repositories.list

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import galstyan.hayk.github.domain.error.AppError
import galstyan.hayk.github.domain.model.Repository
import galstyan.hayk.github.domain.usecase.GetRepositoryList
import galstyan.hayk.github.presentation.error.UiError
import galstyan.hayk.github.threading.MainDispatcherRule
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test


class RepositoryListViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()


    private val getRepositoryList = mockk<GetRepositoryList>()

    private val viewModel by lazy { RepositoryListViewModel(getRepositoryList) }


    @Test
    fun `initially loaded repositories on success`() = runTest {
        val pageSize = 50
        val list = createRepositoryList(pageSize)
        coEvery { getRepositoryList(1) } returns list
        assertEquals(
            RepositoryListUiState.Data(
                RepositoryListUiState.Data.ListData(
                    repositories = list,
                    from = 0,
                    to = pageSize,
                )
            ),
            viewModel.uiStateObservable.value,
        )
    }


    @Test
    fun `on scroll loaded more repositories success`() = runTest {
        val pageSize = 10
        val totalSize = 20
        val totalList = createRepositoryList(totalSize)
        val page1List = totalList.subList(0, pageSize)
        val page2List = totalList.subList(pageSize, totalSize)

        coEvery { getRepositoryList(1) } returns page1List
        coEvery { getRepositoryList(2) } returns page2List

        viewModel.onListScrolledTo(pageSize)

        assertEquals(
            RepositoryListUiState.Data(
                RepositoryListUiState.Data.ListData(
                    repositories = totalList,
                    from = pageSize,
                    to = totalSize,
                )
            ),
            viewModel.uiStateObservable.value,
        )
    }


    @Test
    fun `initially loaded repositories repositoryList correct value`() = runTest {
        val pageSize = 50
        val list = createRepositoryList(pageSize)
        coEvery { getRepositoryList(1) } returns list
        assertEquals(list, viewModel.repositoryList)
    }


    @Test
    fun `network error propagated to ui correctly`() = runTest {
        coEvery { getRepositoryList.invoke(1) } throws AppError.ConnectionError
        assertEquals(
            RepositoryListUiState.Error(UiError.ConnectionError),
            viewModel.uiStateObservable.value
        )
    }

    @Test
    fun `data error propagated to ui correctly`() = runTest {
        coEvery { getRepositoryList.invoke(1) } throws AppError.DataError
        assertEquals(
            RepositoryListUiState.Error(UiError.UnknownError),
            viewModel.uiStateObservable.value
        )
    }

    @Test
    fun `unknown error propagated to ui correctly`() = runTest {
        coEvery { getRepositoryList.invoke(1) } throws AppError.UnknownError
        assertEquals(
            RepositoryListUiState.Error(UiError.UnknownError),
            viewModel.uiStateObservable.value
        )
    }

    @Test
    fun `non app error propagated to ui correctly`() = runTest {
        coEvery { getRepositoryList.invoke(1) } throws RuntimeException()
        assertEquals(
            RepositoryListUiState.Error(UiError.UnknownError),
            viewModel.uiStateObservable.value
        )
    }


    private fun createRepositoryList(size: Int): List<Repository> = ArrayList<Repository>()
        .apply {
            repeat(size) { add(createRepository(it.toLong())) }
        }

    private fun createRepository(id: Long) = Repository(
        id = id,
        name = "",
        description = "",
        stars = 0, forks = 0,
        language = null,
        link = ""
    )
}