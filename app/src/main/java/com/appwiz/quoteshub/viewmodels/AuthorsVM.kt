package com.appwiz.quoteshub.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.appwiz.quoteshub.models.Author
import com.appwiz.quoteshub.repositories.AuthorsRepo
import com.appwiz.quoteshub.room.entity.AuthorEntity
import com.appwiz.quoteshub.services.OperationCallback
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class AuthorsVM(private val repository: AuthorsRepo): ViewModel() {
    private val authorEntities:MutableList<AuthorEntity> = mutableListOf()

    private val _emptyAuthors = MutableLiveData<Boolean>()
    val emptyAuthors: LiveData<Boolean> = _emptyAuthors

    private val _onMessageError = MutableLiveData<Any>()
    val onMessageError: LiveData<Any> = _onMessageError

    fun fetchFromApi(page: Int, letter: String) {
        repository.retrieveAuthors(page, letter, object : OperationCallback {
            override fun onSuccess(obj: Any?) {
                val authors = obj as List<Author>
                for (author in authors) {
                    authorEntities.add(AuthorEntity(author.id, author.name, author.quotes, author.image))
                }
                CoroutineScope(IO).launch {
                    repository.addAuthorsToDB(authorEntities)
                }
            }

            override fun onError(obj: Any?) {
                _onMessageError.postValue(obj)
            }

        })
    }

     suspend fun fetchFromCache(letter: String) : List<AuthorEntity> {
        return repository.getAuthors(letter)
    }
}