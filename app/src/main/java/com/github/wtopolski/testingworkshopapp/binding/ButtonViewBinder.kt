package com.github.wtopolski.testingworkshopapp.binding

import java.util.concurrent.TimeUnit

import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.subjects.PublishSubject

class ButtonViewBinder(enabled: Boolean, visibility: Boolean) : TextViewBinder(enabled, visibility) {

    private val buttonObservable = PublishSubject.create<Any>()

    fun rxClick(scheduler: Scheduler): Observable<Any> {
        return buttonObservable.debounce(250, TimeUnit.MILLISECONDS).observeOn(scheduler)
    }

    fun onClick() {
        buttonObservable.onNext(null)
    }
}
