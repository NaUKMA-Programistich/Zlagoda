package zlagoda.ukma.edu.ua.data.category

import kotlinx.coroutines.flow.Flow
import zlagoda.ukma.edu.ua.db.Category

interface CategoryRepository {

    suspend fun getCategoryById(id: Long): Category?

    fun getAllCategories(): Flow<List<Category>>

    suspend fun deleteCategoryById(id: Long)

    suspend fun insertCategory(id: Long? = null, name: String)
}