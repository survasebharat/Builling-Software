package com.nt.service;

import com.nt.io.RazorpayOrderResponse;
import com.razorpay.RazorpayException;

public interface RazorpayService {

		
	 	RazorpayOrderResponse createOrder(Double amount ,String currency)throws RazorpayException;
}
