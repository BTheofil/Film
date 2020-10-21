package com.example.films.api.converters

import com.example.films.api.model.ApiCategory
import com.example.films.api.model.ApiCategoryList
import com.example.films.model.Category
import com.example.films.model.CategoryList

object ApiCategoryModelConverter {

    fun apiCategoryModelToCategoryModel(apiCategory: ApiCategory): Category =
        Category(
            id = apiCategory.id,
            name = apiCategory.name
        )

    fun categoryModelToApiCategoryModel(category: Category): ApiCategory =
        ApiCategory(
            id = category.id,
            name = category.name
        )

    fun apiCategoryListToCategoryList(apiCategoryList: ApiCategoryList): CategoryList {
        val categories = ArrayList<Category>()
        apiCategoryList.list?.forEach { apiCategory ->
            categories.add(apiCategoryModelToCategoryModel(apiCategory))
        }
        return  CategoryList(categories)
    }
}