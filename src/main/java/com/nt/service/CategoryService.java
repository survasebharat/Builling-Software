package com.nt.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.nt.io.CategoryRequest;
import com.nt.io.CategoryResponse;

public interface CategoryService {
	
	//Create add the Categary
	CategoryResponse add(CategoryRequest request,MultipartFile file);
	//read a Category
	List<CategoryResponse> read();
	//delete a Categary
	public void delete(String categoryId);
	
	
	
}
