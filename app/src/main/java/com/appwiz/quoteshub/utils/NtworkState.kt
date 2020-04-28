package com.appwiz.quoteshub.utils

class NetworkState private constructor(var status: Status, var msg: String) {
    var code: Int = 0

    enum class Status {
        RUNNING,
        SUCCESS,
        FAILED,
        NO_DATA
    }

    companion object {

        var LOADED = NetworkState(Status.SUCCESS, "")
        var LOADING = NetworkState(Status.RUNNING, "")
        fun error(msg: String): NetworkState {
            return NetworkState(Status.FAILED, msg)
        }

        fun errorData(msg: String): NetworkState {
            return NetworkState(Status.NO_DATA, msg)
        }
    }
}