package com.kiyosuke.resulter

import java.io.Serializable


sealed class Result<out T> : Serializable {
    data class Ok<T>(val data: T) : Result<T>(), Serializable
    object Cancel : Result<Nothing>(), Serializable
}

inline fun <T> Result<T>.onOk(f: (data: T) -> Unit): Result<T> = apply { if (this is Result.Ok) f(data) }

inline fun <T> Result<T>.onCancel(f: () -> Unit): Result<T> = apply {
    if (this is Result.Cancel) f()
}

inline fun <T> Result<T>.fold(onOk: (data: T) -> Unit, onCancel: () -> Unit) {
    when (this) {
        is Result.Ok -> onOk(data)
        Result.Cancel -> onCancel()
    }
}