package com.github.wtopolski.testingworkshopapp.binding

import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit

class ButtonViewBinder(enabled: Boolean, visibility: Boolean) : TextViewBinder(enabled, visibility) {

    private val buttonObservable = PublishSubject.create<Long>()

    fun rxClick(scheduler: Scheduler): Observable<Long> {
        return buttonObservable.debounce(50, TimeUnit.MILLISECONDS).observeOn(scheduler)
    }

    fun onClick() {
        buttonObservable.onNext(System.currentTimeMillis())
    }
}
