package com.appwiz.quoteshub.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.appwiz.quoteshub.models.Author
import com.appwiz.quoteshub.repositories.AuthorsRepo
import com.appwiz.quoteshub.services.OperationCallback

class AuthorsVM(private val repository: AuthorsRepo): ViewModel() {
    private val _authors = MutableLiveData<List<Author>>()
    val authors: LiveData<List<Author>> = _authors

    private val _emptyAuthors = MutableLiveData<Boolean>()
    val emptyAuthors: LiveData<Boolean> = _emptyAuthors

    private val _onMessageError = MutableLiveData<Any>()
    val onMessageError: LiveData<Any> = _onMessageError

    fun fetchFromApi(page: Int, letter: String) {
        repository.retrieveAuthors(page, letter, object : OperationCallback {
            override fun onSuccess(obj: Any?) {
                val authors = obj as List<Author>
                if (authors.isNullOrEmpty()) {
                    _emptyAuthors.postValue(true)
                } else {
                    _authors.value = authors
                    _emptyAuthors.postValue(false)
                }
            }

            override fun onError(obj: Any?) {
                _onMessageError.postValue(obj)
            }

        })
    }
}