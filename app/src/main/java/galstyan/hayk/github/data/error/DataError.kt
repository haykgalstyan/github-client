package galstyan.hayk.github.data.error

import galstyan.hayk.github.domain.error.AppError

sealed class DataError : Exception() {
    object ConnectionError : DataError()
    object NotFoundError : DataError()
    object UnknownError : DataError()

    fun toDomain() = when (this) {
        ConnectionError -> AppError.ConnectionError
        NotFoundError -> AppError.DataError
        UnknownError -> AppError.UnknownError
    }
}