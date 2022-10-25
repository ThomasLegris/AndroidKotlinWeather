package com.example.androidkotlinweather.models

/// Create a sealed interface which manage every type of result as real object.
/// Sealed class usefull to have an enum class behavior with typed object.
sealed interface ResultState<out T> {
    data class Success<out T>(val data: T) : ResultState<T>
    data class Error(val exception: Exception) : ResultState<Nothing>

    // Loading is marked `object` because no variable.
    object Loading : ResultState<Nothing>
}