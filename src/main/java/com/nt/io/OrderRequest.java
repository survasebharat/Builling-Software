package com.nt.io;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderRequest {

	private String customerName;
	private String phoneNumber;
	private List<OrderResponse.OrderItemReponse> items;
	private Double subTotal;
	private Double tax;
	private Double grandTotal;
	private String paymentMethod;
	
	
	public static class OrderItemRequest{
		
		private String itemId;
		private String name;
		private double price;
		private Integer quantity;
		
		
	}
	
}
