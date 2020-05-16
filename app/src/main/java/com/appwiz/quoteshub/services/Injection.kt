package com.appwiz.quoteshub.services

import com.appwiz.quoteshub.repositories.*
import com.appwiz.quoteshub.room.dao.CatDao

object Injection {
    fun getCategoriesRepo(dao: CatDao) : CategoriesRepo {
        return CategoriesRepo(dao)
    }
}