package com.appwiz.quoteshub.services

import com.appwiz.quoteshub.repositories.*
import com.appwiz.quoteshub.room.dao.CatDao
import com.appwiz.quoteshub.room.dao.FavDao
import com.appwiz.quoteshub.room.dao.HomeDao

object Injection {

    fun getHomeQuotesRepo(dao: HomeDao) : HomeQuotesRepo {
        return HomeQuotesRepo(dao)
    }

    fun getCategoriesRepo(dao: CatDao) : CategoriesRepo {
        return CategoriesRepo(dao)
    }

    fun getSingleTagRepo() : SingleTagRepo {
        return SingleTagRepo()
    }

    fun getFavoritesRepo(dao: FavDao) : FavRepo {
        return FavRepo(dao)
    }

    fun getAuthorsRepo() : AuthorsRepo {
        return AuthorsRepo()
    }
}