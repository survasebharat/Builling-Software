package com.nt.io;

import java.time.LocalDateTime;
import java.util.List;

import com.nt.io.OrderRequest.OrderItemRequest;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderResponse {

	private String orderId;
	private String customerName;
	private String phoneNumber;
	private List<OrderItemRequest>  items;
	private Double subTotal;
	private Double tax;
	private Double grandTotal;
	private Paymentmethod paymentMethod;
	private LocalDateTime createdAt;
	private PaymentDetails paymentDetails;
	
	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	@Builder
	public static class OrderItemReponse{
		
		private String itemId;
		private String name;
		private double price;
		private Integer quantity;
		
		
	}
}
