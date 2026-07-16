package com.example.course_ventures.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.course_ventures.entity.Category;
import com.example.course_ventures.repository.CategoryRepository;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository repo;

    // Save Category
    public Category saveCategory(Category category) 
    {
        return repo.save(category);
    }

    // Find Category By Id
    public Category findCategoryById(int id)
    {
        return repo.findById(id).orElseThrow(() -> new RuntimeException("Category Not Found"));
    }

    // Find All Categories
    public List<Category> findAllCategory() 
    {
        return repo.findAll();
    }

    // Update Category
    public Category updateCategory(int id, Category category) 
    {
        Category dbCategory = findCategoryById(id);
        dbCategory.setCategoryName(category.getCategoryName());
        dbCategory.setCategoryDescription(category.getCategoryName());
        return repo.save(dbCategory);
    }
    
    // Delete Category
    public String deleteCategory(int id)
    {
        Category category = findCategoryById(id);
        repo.delete(category);
        return "Category Deleted Successfully";
    }

}