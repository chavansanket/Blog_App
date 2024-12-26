package com.project1.blog.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project1.blog.payloads.ApiResponse;
import com.project1.blog.payloads.CategoryDto;
import com.project1.blog.services.CategoryService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/apis/categories")
public class CategoryController {
	
	private final CategoryService categoryService;
	
	private CategoryController(@Qualifier("categoryService") CategoryService catService) {
		this.categoryService = catService;
	}
	
	
	@PostMapping("/")
	public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto catDto){
		CategoryDto createdCatDto=categoryService.createCategory(catDto);
		return new ResponseEntity<CategoryDto>(createdCatDto, HttpStatus.CREATED);
				
	}
	
	@PutMapping("/{catId}")
	public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto catDto, @PathVariable Integer catId){
		CategoryDto updatedCatDto=categoryService.updateCategory(catDto, catId);
		return new ResponseEntity<CategoryDto>(updatedCatDto, HttpStatus.OK);
		
	}
	
	@DeleteMapping("/{catId}")
	public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Integer catId){
		this.categoryService.deleteCategory(catId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("category deleted successfully !!", true),HttpStatus.OK);
	}
	
	
	@GetMapping("/{catId}")
	public ResponseEntity<CategoryDto> getCategory(@PathVariable Integer catId){
		CategoryDto catDto= this.categoryService.getCategory(catId);
		return new ResponseEntity<CategoryDto>(catDto, HttpStatus.OK);
	}
	
	@GetMapping("/")
	public ResponseEntity<List<CategoryDto>> getAllCategory(){
//		return new ResponseEntity<List<CategoryDto>>(this.catService.getCategories(), HttpStatus.OK);
		return ResponseEntity.ok(this.categoryService.getCategories());
	}

}
