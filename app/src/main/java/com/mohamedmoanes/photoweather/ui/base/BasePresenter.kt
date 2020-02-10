package com.mohamedmoanes.photoweather.ui.base

import com.mohamedmoanes.photoweather.data.network.errorHandler
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

open class BasePresenter {
    private var disposables = CompositeDisposable()

    protected fun <T> singleSubscribe(single: Single<T>, result: ResultListener<T>): Disposable {
        val disposable = single.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ response: T -> result.onSuccess(response) }
            ) { throwable: Throwable? -> errorHandler(throwable!!, result) }
        disposables.add(disposable)
        return disposable
    }

   open fun clear() {
        disposables.dispose()
    }
}