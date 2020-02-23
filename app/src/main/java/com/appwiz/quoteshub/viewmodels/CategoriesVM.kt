package com.appwiz.quoteshub.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.appwiz.quoteshub.models.Category
import com.appwiz.quoteshub.repositories.CategoriesRepo
import com.appwiz.quoteshub.room.entity.CatEntity
import com.appwiz.quoteshub.services.OperationCallback
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlin.concurrent.thread

class CategoriesVM(private val repository: CategoriesRepo) : ViewModel() {
    val categories: LiveData<List<CatEntity>> = repository.categories

    private val _onMessageError = MutableLiveData<Any>()
    val onMessageError: LiveData<Any> = _onMessageError

    fun fetchFromApi() {
        repository.retrieveCategories(object : OperationCallback {

            override fun onSuccess(obj: Any?) {
                if (obj != null) {
                    val catEntities:MutableList<CatEntity> = ArrayList()
                    val apiCategories = obj as List<Category>
                    for (cat in apiCategories) {
                        val catEntity =
                            CatEntity(cat.id, cat.name, cat.quotes)
                        catEntities.add(catEntity)
                    }

                    CoroutineScope(IO).launch {
                        repository.addCatToDB(catEntities)
                    }

                }
            }

            override fun onError(obj: Any?) {
                MainScope().launch {
                    val size = repository.checkEmpty()
                    if (size == 0) {
                        _onMessageError.postValue(obj)
                    }
                }
            }

        })
    }
}