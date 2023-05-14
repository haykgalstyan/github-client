package galstyan.hayk.github.presentation.error

import androidx.annotation.StringRes
import galstyan.hayk.github.R
import galstyan.hayk.github.domain.error.AppError

sealed class UiError(@StringRes val errorRes: Int) {
    object ConnectionError : UiError(R.string.error_connection)
    object UnknownError : UiError(R.string.error_unknown)

    companion object {
        fun from(error: Exception) = when (error) {
            is AppError.ConnectionError -> ConnectionError
            else -> UnknownError
        }
    }
}