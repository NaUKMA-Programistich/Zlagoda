package zlagoda.ukma.edu.ua.di

import DriverFactory
import zlagoda.ukma.edu.ua.data.category.CategoryRepository
import zlagoda.ukma.edu.ua.data.category.CategoryReposiryImpl
import zlagoda.ukma.edu.ua.db.MyDatabase

object Singleton {
    private val database = MyDatabase(DriverFactory.createDriver())
    val categoryRepository: CategoryRepository = CategoryReposiryImpl(database)
}