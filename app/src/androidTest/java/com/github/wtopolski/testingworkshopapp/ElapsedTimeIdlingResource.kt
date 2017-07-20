package com.github.wtopolski.testingworkshopapp

import android.support.test.espresso.IdlingResource.ResourceCallback
import android.support.test.espresso.IdlingResource

class ElapsedTimeIdlingResource(private val waitingTime: Long) : IdlingResource {
    private val startTime: Long = System.currentTimeMillis()
    private var resourceCallback: ResourceCallback? = null

    override fun getName(): String {
        return ElapsedTimeIdlingResource::class.java.name + ":" + waitingTime
    }

    override fun isIdleNow(): Boolean {
        val elapsed = System.currentTimeMillis() - startTime
        val idle = elapsed >= waitingTime
        if (idle) {
            resourceCallback?.onTransitionToIdle()
        }
        return idle
    }

    override fun registerIdleTransitionCallback(resourceCallback: ResourceCallback) {
        this.resourceCallback = resourceCallback
    }
}