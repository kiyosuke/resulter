package com.kiyosuke.resulter

interface ResultCallback<T> {
    fun onResult(result: Result<T>)
}