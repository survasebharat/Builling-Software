package com.nt.service.impl;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.nt.io.RazorpayOrderResponse;
import com.nt.service.RazorpayService;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class RazorpayServiceImpl implements RazorpayService {

	@Value("${razorpay.key.id}")
	private String razorpayKeyId;
	
	@Value("${razorpay.key.id}")
	private String razorpayKeySecrict;
	
	@Override
	public RazorpayOrderResponse createOrder(Double amount, String currency) throws RazorpayException {
		RazorpayClient razorpayClient = new 	RazorpayClient(razorpayKeyId, razorpayKeySecrict);
		JSONObject orderRequest = new JSONObject();
		orderRequest.put("amount", amount*100);
		orderRequest.put("currency", currency);
		orderRequest.put("receipt", "order rcptid"+System.currentTimeMillis());
		orderRequest.put("payment_capture", 1);
		
		Order order =razorpayClient.orders.create(orderRequest);
		
		return convertToResponse(order);
	}

	private RazorpayOrderResponse convertToResponse(Order order) {
		return 	RazorpayOrderResponse.builder()
					.id(order.get("id"))
					.entity(order.get("entity"))
					.amount(order.get("amount"))
					.currency(order.get("status"))
					.status(order.get("created_at"))
					.receipt(order.get("receipt"))
					.build();
	
	}

}
