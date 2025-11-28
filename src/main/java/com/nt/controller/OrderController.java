package com.nt.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.nt.io.OrderRequest;
import com.nt.io.OrderResponse;
import com.nt.service.OrderService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

	private final OrderService orderService;
	
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping
	public OrderResponse createOrder(@RequestBody OrderRequest request)
	{
		return orderService.createOrder(request);
	}
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping("/{orderId}")
	public void deleteOrder(@PathVariable String orderId)
	{
		orderService.deleteOrder(orderId);
	}
	@GetMapping("/latest")
	public List<OrderResponse> getLatestOrder()
	{
		return orderService.getLatestOrders();
	}
	
}
