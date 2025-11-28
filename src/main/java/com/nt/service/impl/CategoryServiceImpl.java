package com.nt.service.impl;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.nt.entity.CategoryEntity;
import com.nt.io.CategoryRequest;
import com.nt.io.CategoryResponse;
import com.nt.repository.CategoryRepository;
import com.nt.repository.ItemRepository;
import com.nt.service.CategoryService;
import com.nt.service.FileUploadService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService{

	private final CategoryRepository categoryRepository;
	private final FileUploadService fileUploadService;
	
	private final ItemRepository itemRepository;
	@Override
	public CategoryResponse add(CategoryRequest request, MultipartFile file) {
		
		String imgUrl  = fileUploadService.uploadFile(file);
		CategoryEntity newCategory = ConvertToEntity(request);
		newCategory .setImgUrl(imgUrl);
		newCategory = categoryRepository.save(newCategory);
		return convertToResponse(newCategory);
		
	}

	private CategoryResponse convertToResponse(CategoryEntity newCategory) {
		
		Integer itemCount =itemRepository.countByCategoryId(newCategory.getId());
	return CategoryResponse.builder()
		.categoryId(newCategory.getCategoryId())
		.name(newCategory.getName())
		.description(newCategory.getDescription())
		.bgColor(newCategory.getBgColor())
		.imgUrl(newCategory.getImgUrl())
		.createdAt(newCategory.getCreatedAt())
		.updatedAt(newCategory.getUpdatedAt())
		.items(itemCount)
		.build();
		
	}

	private CategoryEntity ConvertToEntity(CategoryRequest request) {
		return CategoryEntity.builder()
				.categoryId(UUID.randomUUID().toString())
				.name(request.getName())
				.description(request.getDescription())
				.bgColor(request.getBgColor())
				.build();
				
	}

	@Override
	public List<CategoryResponse> read() {	
		return  categoryRepository.findAll().stream()
				.map(CategoryEntity ->convertToResponse(CategoryEntity))
				.collect(Collectors.toList());
	}
	//delete category Id
	@Override
	public void delete(String categoryId) {
		
		CategoryEntity existCategory = categoryRepository.findByCategoryId(categoryId)
				.orElseThrow(()-> new RuntimeException("Categary Not Found"+ categoryId));
		
		fileUploadService.deleteFile(existCategory.getImgUrl());
		categoryRepository.delete(existCategory);
	}

}
