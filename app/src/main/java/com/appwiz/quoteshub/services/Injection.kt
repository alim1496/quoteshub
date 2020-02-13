package com.appwiz.quoteshub.services

import com.appwiz.quoteshub.repositories.CategoriesRepo
import com.appwiz.quoteshub.repositories.HomeQuotesRepo
import com.appwiz.quoteshub.room.CatDao

object Injection {

    fun getHomeQuotesRepo() : HomeQuotesRepo {
        return HomeQuotesRepo()
    }

    fun getCategoriesRepo(dao: CatDao) : CategoriesRepo {
        return CategoriesRepo(dao)
    }

}