package com.appwiz.quoteshub.services

import com.appwiz.quoteshub.repositories.CategoriesRepo
import com.appwiz.quoteshub.repositories.HomeQuotesRepo
import com.appwiz.quoteshub.room.dao.CatDao
import com.appwiz.quoteshub.room.dao.HomeDao

object Injection {

    fun getHomeQuotesRepo(dao: HomeDao) : HomeQuotesRepo {
        return HomeQuotesRepo(dao)
    }

    fun getCategoriesRepo(dao: CatDao) : CategoriesRepo {
        return CategoriesRepo(dao)
    }

}