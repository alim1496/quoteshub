package com.appwiz.quoteshub.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.appwiz.quoteshub.models.FeedModel
import com.appwiz.quoteshub.repositories.HomeQuotesRepo
import com.appwiz.quoteshub.services.OperationCallback

class HomeQuotesVM(private val repository: HomeQuotesRepo) : ViewModel() {
    private val _quotes = MutableLiveData<FeedModel>()
    val quotes: LiveData<FeedModel> = _quotes

    private val _isViewLoading = MutableLiveData<Boolean>()
    val isViewLoading:LiveData<Boolean> = _isViewLoading

    private val _onMessageError = MutableLiveData<Any>()
    val onMessageError:LiveData<Any> = _onMessageError

    fun loadHomeData() {
        _isViewLoading.postValue(true)
        repository.retrieveHomeQuotes(object : OperationCallback {

            override fun onSuccess(obj: Any?) {
                _isViewLoading.postValue(false)
                if (obj != null) {
                    _quotes.value = obj as FeedModel
                }
            }

            override fun onError(obj: Any?) {
                _isViewLoading.postValue(false)
                _onMessageError.postValue(obj)
            }

        })
    }
}