package service

import model.Category
import org.springframework.stereotype.Service
import repository.CategoryRepository
import java.util.UUID

@Service
class CategoryService(categoryRepository: CategoryRepository) {
    private val categoryRepository = categoryRepository

    fun getCategories() {
        categoryRepository.findAll()
    }
    fun getCategoryByName(name: String) {
        categoryRepository.findByName(name)
    }
    fun createCategory(name: String) {
        categoryRepository.save(Category(name = name))
    }
    fun deleteCategory(id: UUID) {
        categoryRepository.deleteById(id)
    }
    fun updateCategory(category: Category) {
        categoryRepository.save(category)
    }
}