package com.nt.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.nt.io.ItemRequest;
import com.nt.io.ItemResponse;

public interface ItemService {

	ItemResponse add(ItemRequest request, MultipartFile file);
	
	List<ItemResponse> fetchItems();
	
	void deleteItem(String itemId);
}
