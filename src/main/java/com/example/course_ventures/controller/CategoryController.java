package com.example.course_ventures.controller;

import java.util.List;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jsp.CourseHub.entity.Category;
import com.jsp.CourseHub.service.CategoryService;


@RestController
@RequestMapping("/category")
public class CategoryController {

	@Autowired
	CategoryService categoryService;

	@PostMapping("/save")
	public Category saveCategory(@RequestBody Category category) {
		 System.out.println(category.getCategoryName());
		    System.out.println(category.getDescription());
		return categoryService.saveCategory(category);
	}

	@GetMapping("/fetch")
	public List<Category> getAllCategories() {

		return categoryService.getAllCategories();
	}

	@DeleteMapping("/delete/{id}")
	public String deleteCategory(@PathVariable int id) {

		categoryService.deleteCategory(id);

		return "Category Deleted Successfully";
	}

	@PostMapping("/update/{id}")
	public Category updateCategory(@PathVariable int id, @RequestBody Category category) {
		return categoryService.updateCategory(id, category);
	}
}
