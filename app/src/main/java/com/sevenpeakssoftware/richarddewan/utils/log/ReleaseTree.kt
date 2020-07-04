package com.sevenpeakssoftware.richarddewan.utils.log

import android.util.Log
import com.google.firebase.crashlytics.FirebaseCrashlytics
import timber.log.Timber

class ReleaseTree: Timber.Tree() {

    companion object {
        private const val KEY_PRIORITY = "Priority"
        private const val KEY_TAG = "Tag"
    }

    override fun isLoggable(tag: String?, priority: Int): Boolean {
        return !(priority == Log.DEBUG || priority == Log.VERBOSE || priority == Log.INFO)
    }

    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {

        FirebaseCrashlytics.getInstance().also {
            it.setCustomKey(KEY_PRIORITY,priority)
            tag?.let { t -> it.setCustomKey(KEY_TAG, t) }
            it.log(message)
            t?.let { e -> it.recordException(e) }
        }.sendUnsentReports()
    }

}