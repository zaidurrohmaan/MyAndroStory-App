package com.example.myandrostory.data

//sealed class Result<out R> private constructor() {
//    data class Success<out T>(val data: T) : Result<T>()
//    data class Error(val error: String) : Result<Nothing>()
//    object Loading : Result<Nothing>()
//}

sealed class Result<out T> {
    data class Success<out T>(val data: T? = null) : Result<T>()
    data class Loading(val nothing: Nothing? = null) : Result<Nothing>()
    data class Error(val msg: String?) : Result<Nothing>()
}