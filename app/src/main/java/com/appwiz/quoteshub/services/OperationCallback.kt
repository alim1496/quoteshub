package com.appwiz.quoteshub.services

interface OperationCallback {
    fun onSuccess(obj: Any?)
    fun onError(obj: Any?)
}
