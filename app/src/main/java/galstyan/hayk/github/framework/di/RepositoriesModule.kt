package galstyan.hayk.github.framework.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import galstyan.hayk.github.data.RepositoryDataSource
import galstyan.hayk.github.framework.GithubClientFactory
import galstyan.hayk.github.framework.GithubRepositoryDataSource
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoriesModule {

    @Provides
    @Singleton
    fun provideRepositoriesDataSource(): RepositoryDataSource {
        return GithubRepositoryDataSource(
            GithubClientFactory.repositoriesClient()
        )
    }
}