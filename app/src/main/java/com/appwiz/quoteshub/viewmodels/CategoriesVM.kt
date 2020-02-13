package com.appwiz.quoteshub.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.appwiz.quoteshub.models.Category
import com.appwiz.quoteshub.repositories.CategoriesRepo
import com.appwiz.quoteshub.room.CatEntity
import com.appwiz.quoteshub.services.OperationCallback
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
                        val catEntity = CatEntity(cat.id, cat.name, cat.quotes)
                        catEntities.add(catEntity)
                    }
                    thread(start = true) {
                        repository.addCatToDB(catEntities)
                    }
                }
            }

            override fun onError(obj: Any?) {
                if (categories.value.isNullOrEmpty()) {
                    _onMessageError.postValue(obj)
                }
            }

        })
    }
}