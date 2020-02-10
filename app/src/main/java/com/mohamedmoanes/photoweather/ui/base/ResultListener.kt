package com.mohamedmoanes.photoweather.ui.base

interface ResultListener<T> {
    fun onSuccess(data: T)
    fun onError(message: String)
    fun onFailure(message: String)
}