package com.sevenpeakssoftware.richarddewan.utils.log


import timber.log.Timber

class DebugTree: Timber.DebugTree() {

    override fun createStackElementTag(element: StackTraceElement): String? =
         "(${element.fileName}:${element.lineNumber}) #${element.methodName}"

}