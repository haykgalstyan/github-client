package galstyan.hayk.github.framework.data.github

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import galstyan.hayk.github.BuildConfig
import galstyan.hayk.github.data.RepositoryDataSource
import galstyan.hayk.github.framework.data.github.repositories.RepositoriesApi
import galstyan.hayk.github.framework.data.github.repositories.RepositoriesRemoteDs
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object GithubModule {

    private val clientFactory = GithubClientFactory(
        accessToken = BuildConfig.GITHUB_ACCESS_TOKEN,
        logsEnabled = BuildConfig.DEBUG
    )

    @Provides
    @Singleton
    fun provideRepositoriesDataSource(): RepositoryDataSource {
        return RepositoriesRemoteDs(clientFactory.createClient(RepositoriesApi::class.java))
    }
}