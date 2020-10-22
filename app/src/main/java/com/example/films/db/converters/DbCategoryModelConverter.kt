package com.example.films.db.converters

import com.example.films.db.entity.DbFavouriteCategory
import com.example.films.db.entity.DbFavouriteCategoryList
import com.example.films.model.Category
import com.example.films.model.CategoryList
import kotlin.collections.ArrayList

object DbCategoryModelConverter {

    fun dbCategoryModelToCategoryModel(dbFavouriteCategory: DbFavouriteCategory): Category =
        Category(
            id = dbFavouriteCategory.id,
            name = dbFavouriteCategory.name
        )

    fun dbCategoryListToCategoryList(dbFavouriteCategoryList: DbFavouriteCategoryList): CategoryList {
        val categories = ArrayList<Category>()
        dbFavouriteCategoryList.list?.forEach { dbCategory ->
            categories.add(dbCategoryModelToCategoryModel(dbCategory))
        }
        return CategoryList(categories)
    }
}