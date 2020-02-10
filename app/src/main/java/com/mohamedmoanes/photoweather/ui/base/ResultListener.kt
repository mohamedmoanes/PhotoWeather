package com.mohamedmoanes.photoweather.ui.base

interface ResultListener<T> {
    fun onSuccess(data: T)
    fun onFailure(message: String)
}