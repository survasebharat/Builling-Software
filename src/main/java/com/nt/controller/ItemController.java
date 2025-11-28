package com.nt.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nt.io.ItemRequest;
import com.nt.io.ItemResponse;
import com.nt.service.ItemService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ItemController {

	private final ItemService itemService;
	
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping("/admin/items")
	public ItemResponse addItem(@RequestPart("item") String itemString,
																		@RequestPart("file") MultipartFile file)
	{
		ObjectMapper objectMapper = new ObjectMapper();
		ItemRequest itemRequest = null;
		
		try {
			
			itemRequest = objectMapper.readValue(itemString , ItemRequest.class);
		return 	itemService.add(itemRequest, file); 
		} catch (JsonProcessingException ex) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Error Occured while ");
		}
	}
	
	@GetMapping("/items")
	public List<ItemResponse> readItem()
	{
		return itemService.fetchItems();
	}
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping("/admin/items/{itemId}")
	public void removeItem(@PathVariable String itemId)
	{
		try {
			 itemService.deleteItem(itemId);
		} catch (Exception e) {
			
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Item Not Found");
			
		}
	}
}
