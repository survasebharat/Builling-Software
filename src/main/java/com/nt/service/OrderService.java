package com.nt.service;

import java.awt.print.Pageable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.nt.io.OrderRequest;
import com.nt.io.OrderResponse;
import com.nt.io.PaymentVarificationRequest;

public interface OrderService {

	OrderResponse createOrder(OrderRequest request);
	
	void deleteOrder(String orderId);
	
	List<OrderResponse> getLatestOrders();

	OrderResponse varifyPayment(PaymentVarificationRequest request);
	
	Double sumSalesByDate(LocalDate date);
	Long countByOrderDate(LocalDate date);
	
	List<OrderResponse> findRecentOrders();
	
	
}
