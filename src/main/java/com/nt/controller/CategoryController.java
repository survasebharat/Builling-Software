package com.nt.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nt.io.CategoryRequest;
import com.nt.io.CategoryResponse;
import com.nt.service.CategoryService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class CategoryController {

	private final CategoryService categoryService;
	
	//add category Method
	@PostMapping("/admin/categories")
	@ResponseStatus(HttpStatus.CREATED)
	public CategoryResponse addCategory(@RequestPart("category") String categoryString,
																					@RequestPart("file") MultipartFile file)
	{ 
		ObjectMapper objectMapper = new ObjectMapper();
		CategoryRequest request = null;
		
		try {
				request = objectMapper.readValue(categoryString, CategoryRequest.class);
				return categoryService.add(request,file);
		} catch (JsonProcessingException ex) {
			
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Exception Occured while parsing the json"+ex.getMessage());
		}
		
	}
	
	//read Category Method
	@GetMapping("/read")
	public List<CategoryResponse> fetchCategaries()
	{
		return categoryService.read();
	}
	
	//delete CategaryById
	@ResponseStatus(HttpStatus.NO_CONTENT)
	 @DeleteMapping("/admin/categories/{categoryId}")
	public void remove(@PathVariable String categoryId)
	{
		try {
			categoryService.delete(categoryId);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,e.getMessage());
		}
	}
	 
}
