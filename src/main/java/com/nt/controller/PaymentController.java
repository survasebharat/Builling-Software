package com.nt.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.nt.io.OrderResponse;
import com.nt.io.PaymentRequest;
import com.nt.io.PaymentVarificationRequest;
import com.nt.io.RazorpayOrderResponse;
import com.nt.service.OrderService;
import com.nt.service.RazorpayService;
import com.razorpay.RazorpayException;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
public class PaymentController {
	
		private final RazorpayService razorpayService;
		private final OrderService orderService;
		
		@PostMapping("/create-order")
		@ResponseStatus(HttpStatus.CREATED)
		public RazorpayOrderResponse createRazorpayOrder(@RequestBody PaymentRequest request) throws RazorpayException
		{
				return razorpayService.createOrder(request.getAmount(), request.getCurrency());
		}
		
		@PostMapping("/verify")
		public OrderResponse varifyPayment(@RequestBody PaymentVarificationRequest request)
		{
			return orderService.varifyPayment(request);
		}
		
	
}
