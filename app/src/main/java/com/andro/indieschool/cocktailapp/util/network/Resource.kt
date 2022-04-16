package com.andro.indieschool.cocktailapp.util.network

sealed class Resource<out T> {

    abstract val isComplete: Boolean

    data class Success<out T>(
        val data: T,
        val isCached: Boolean = false
    ) : Resource<T>() {
        override val isComplete: Boolean
            get() = !isCached

        operator fun invoke(): T = data
    }

    data class Error<out T, out E>(
        val data: T?,
        val error: E?
    ) : Resource<T>() {
        override val isComplete: Boolean
            get() = true
    }

    object Loading : Resource<Nothing>() {
        override val isComplete: Boolean
            get() = false
    }

    object Uninitialized : Resource<Nothing>() {
        override val isComplete: Boolean
            get() = false
    }

}

operator fun<T> Resource<T>.invoke(): T? {
    return when {
        this is Resource.Success -> this.data
        this is Resource.Error<T, *> && this.data != null -> this.data
        else -> null
    }
}
