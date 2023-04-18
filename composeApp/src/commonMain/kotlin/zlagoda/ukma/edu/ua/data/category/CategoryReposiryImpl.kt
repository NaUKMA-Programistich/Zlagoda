package zlagoda.ukma.edu.ua.data.category

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import zlagoda.ukma.edu.ua.db.Category
import zlagoda.ukma.edu.ua.db.MyDatabase

class CategoryReposiryImpl(
    db: MyDatabase
): CategoryRepository {

    private val queries = db.categoryQueries

    override suspend fun getCategoryById(id: Long): Category? {
        return withContext(Dispatchers.IO) {
            queries.getCategoryById(id).executeAsOneOrNull()
        }
    }

    override fun getAllCategories(): Flow<List<Category>> {
        return queries.getAllCategories().asFlow().mapToList(Dispatchers.IO)
    }

    override suspend fun deleteCategoryById(id: Long) {
        withContext(Dispatchers.IO) {
            queries.deleteCategoryById(id)
        }
    }

    override suspend fun insertCategory(id: Long?, name: String) {
        withContext(Dispatchers.IO) {
            queries.insertCategory(id, name)
        }
    }
}