package com.merryblue.myvoicealarm.common.extenstion

import androidx.lifecycle.MutableLiveData
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.*
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers

fun Disposable.addToCompositeDisposable(compositeDisposable: CompositeDisposable) {
    compositeDisposable.add(this)
}

fun <T> observableTransformer(status: MutableLiveData<LOADING>? = null): ObservableTransformer<T, T> {
    return ObservableTransformer { observable ->
        observable
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { status?.value = LOADING.START }
            .doFinally { status?.value = LOADING.END }
    }
}


//Observable
fun <T : Any> Observable<T>.applyIoScheduler(status: MutableLiveData<LOADING>? = null) =
    applyScheduler(Schedulers.io()).doOnSubscribe { status?.value = LOADING.START }
        .doFinally { status?.value = LOADING.END }

fun <T : Any> Observable<T>.applyComputationScheduler(status: MutableLiveData<LOADING>? = null) =
    applyScheduler(Schedulers.computation()).doOnSubscribe {
        status?.value = LOADING.START
    }
        .doFinally { status?.value = LOADING.END }

private fun <T : Any> Observable<T>.applyScheduler(scheduler: Scheduler) =
    subscribeOn(scheduler).observeOn(AndroidSchedulers.mainThread())


//Flowable
fun <T : Any> Flowable<T>.applyIoScheduler(status: MutableLiveData<LOADING>? = null) =
    applyScheduler(Schedulers.io()).doOnSubscribe { status?.value = LOADING.START }
        .doFinally { status?.value = LOADING.END }

fun <T : Any> Flowable<T>.applyComputationScheduler(status: MutableLiveData<LOADING>? = null) =
    applyScheduler(Schedulers.computation()).doOnSubscribe {
        status?.value = LOADING.START
    }
        .doFinally { status?.value = LOADING.END }

private fun <T : Any> Flowable<T>.applyScheduler(scheduler: Scheduler) =
    subscribeOn(scheduler).observeOn(AndroidSchedulers.mainThread())


//Single
fun <T : Any> Single<T>.applyIoScheduler(status: MutableLiveData<LOADING>? = null) =
    applyScheduler(Schedulers.io()).doOnSubscribe { status?.value = LOADING.START }
        .doFinally { status?.value = LOADING.END }


fun <T : Any> Single<T>.applyComputationScheduler(status: MutableLiveData<LOADING>? = null) =
    applyScheduler(Schedulers.computation()).doOnSubscribe {
        status?.value = LOADING.START
    }
        .doFinally { status?.value = LOADING.END }

private fun <T : Any> Single<T>.applyScheduler(scheduler: Scheduler) =
    subscribeOn(scheduler).observeOn(AndroidSchedulers.mainThread())


//Maybe
fun <T : Any> Maybe<T>.applyIoScheduler(status: MutableLiveData<LOADING>? = null) =
    applyScheduler(Schedulers.io()).doOnSubscribe { status?.value = LOADING.START }
        .doFinally { status?.value = LOADING.END }

fun <T : Any> Maybe<T>.applyComputationScheduler(status: MutableLiveData<LOADING>? = null) =
    applyScheduler(Schedulers.computation()).doOnSubscribe {
        status?.value = LOADING.START
    }
        .doFinally { status?.value = LOADING.END }

private fun <T : Any> Maybe<T>.applyScheduler(scheduler: Scheduler) =
    subscribeOn(scheduler).observeOn(AndroidSchedulers.mainThread())


enum class LOADING {
    START, END
}