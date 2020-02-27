package com.appwiz.quoteshub.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.appwiz.quoteshub.models.Quote
import com.appwiz.quoteshub.repositories.SingleTagRepo
import com.appwiz.quoteshub.services.OperationCallback

class SingleTagVM(private val repository:SingleTagRepo) : ViewModel() {
    private val _tagQuotes = MutableLiveData<List<Quote>>()
    val tagQuotes:LiveData<List<Quote>> = _tagQuotes

    private val _onMessageError = MutableLiveData<Any>()
    val onMessageError: LiveData<Any> = _onMessageError

    fun fetchFromApi(id: Int, page: Int) {
        repository.retrieveTags(id, page, object : OperationCallback {
            override fun onSuccess(obj: Any?) {
                val tags = obj as List<Quote>
                _tagQuotes.value = tags
            }

            override fun onError(obj: Any?) {
                _onMessageError.postValue(obj)
            }

        })
    }
}