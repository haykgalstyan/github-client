package galstyan.hayk.github.domain.error

sealed class AppError : Exception() {
    object DataError : AppError()
    object ConnectionError : AppError()
    object UnknownError : AppError()
}