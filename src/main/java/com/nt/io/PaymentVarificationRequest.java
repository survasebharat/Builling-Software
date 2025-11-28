package com.nt.io;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentVarificationRequest {

	
	private String razorpayOrderId;
	private String razorpayPaymentId;
	private String razorpaySignature;
	private String orderId;
}
