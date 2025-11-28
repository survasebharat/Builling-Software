package com.nt.service.impl;

import java.awt.print.Pageable;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.nt.entity.OrderEntity;
import com.nt.entity.OrderItemEntity;
import com.nt.io.OrderRequest;
import com.nt.io.OrderRequest.OrderItemRequest;
import com.nt.io.OrderResponse;
import com.nt.io.PaymentDetails;
import com.nt.io.PaymentVarificationRequest;
import com.nt.io.Paymentmethod;
import com.nt.repository.OrderEntityRepository;
import com.nt.service.OrderService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService  
{
	private final OrderEntityRepository orderEntityRepository;
	private final PaymentDetails paymentDetails;
	private final OrderItemRequest orderItemRequest;
	private final OrderEntity orderEntity;
	@Override
	public OrderResponse createOrder(OrderRequest request) {
		
		OrderEntity newOrder = convertToOrderEntity(request);
		PaymentDetails paymentDetails = new PaymentDetails();
		paymentDetails.setStatus(newOrder.getPaymentMethod()== Paymentmethod.CASH ? paymentDetails.PaymentStatus.COMPLETED: paymentDetails.PaymentStatus.PENDING);
		newOrder.setPaymentDetails(paymentDetails);
		
       List<OrderItemEntity> orderItems = request.getCartItems().stream()
				.map(this::convertToOrderItemEntity)
				.collect(Collectors.toList());
		newOrder = orderEntityRepository.save(newOrder);
		return convertToResponse(newOrder);
	}

	private OrderResponse convertToResponse(OrderEntity newOrder) {
		
		
		return OrderResponse.builder()
				.orderId(newOrder.getOrderId())
				.customerName(newOrder.getCustomerName())
				.phoneNumber(newOrder.getPhoneNumber())
				.subTotal(newOrder.getSubTotal())
				.tax(newOrder.getTax())
				.grandTotal(newOrder.getGroundTotal())
				.paymentMethod(newOrder.getPaymentMethod())
				.items(newOrder.getItem().stream()
						.map(this:: convertToItemResponse)
						.collect(Collectors.toList()))
				.paymentDetails(newOrder.getPaymentDetails())
				.createdAt(newOrder.getCreatedAt())
				.build();
				
	}
//	private OrderItemEntity convertToItemResponse(OrderItemEntity orderItemEntity)
//	{
//		return OrderItemEntity.builder()
//				.itemId(orderItemRequest.getItemId())
//				.name(orderItemRequest.getName())
//				.price(orderItemRequest.getPrice())
//				.quantity(orderItemRequest.getQuantity())
//				.build();
//	}

	private OrderEntity convertToOrderEntity(OrderRequest request) {
		return 	OrderEntity.builder()
				.customerName(request.getCustomerName())
				.phoneNumber(request.getPhoneNumber())
				.subTotal(request.getSubTotal())
				.tax(request.getTax())
				.groundTotal(request.getGrandTotal())
				.paymentMethod(Paymentmethod.valueOf(request.getPaymentMethod()))
				.build();
		
	}

	public OrderResponse.OrderItemReponse convertToItemResponse(OrderItemEntity orderItemEntity)
	{
		return	OrderResponse.OrderItemReponse.builder()
					.itemId(orderItemEntity.getItemId())
					.name(orderItemEntity.getName())
					.price(orderItemEntity.getPrice())
					.quantity(orderItemEntity.getQuantity())
					.build();
	}
	@Override
	public void deleteOrder(String orderId) {
		OrderEntity extendingOrder = orderEntityRepository.findByOrderId(orderId)
				.orElseThrow(()-> new RuntimeException("Order Not Found"));
		orderEntityRepository.delete(extendingOrder);
	}

	@Override
	public List<OrderResponse> getLatestOrders() {
	
		return orderEntityRepository.findAllByOrderByCreatedAtDesc()
				.stream()
				.map(this::convertToItemResponse)
				.collect(Collectors.toList());
	}

	@Override
	public OrderResponse varifyPayment(PaymentVarificationRequest request) {
		OrderEntity existOrder  = orderEntityRepository.findByOrderId(request.getOrderId())
				.orElseThrow(()-> new RuntimeException("Order Not Found"));
		
		if(!varifyRazorpaySignature(request.getRazorpayOrderId(),
				request.getRazorpayPaymentId(),
				request.getRazorpaySignature()))
		{
			throw new RuntimeException("payment varification Faoiled");
		}
		PaymentDetails paymentDetails = existOrder.getPaymentDetails();
		paymentDetails.setRazorpayOrderId(request.getRazorpayOrderId());
		paymentDetails.setRazorpayPaymentId(request.getRazorpayPaymentId());
		paymentDetails.setRazorpaySignature(request.getRazorpaySignature());
		paymentDetails.setStatus(PaymentDetails.PaymentStatus.COMPLETED);
		
		existOrder = orderEntityRepository.save(existOrder);
		
		return convertToResponse(existOrder);
		
	}

	private boolean varifyRazorpaySignature(String razorpayOrderId, String razorpayPaymentId,
			String razorpaySignature) {
		
		return true;
	}

	@Override
	public Double sumSalesByDate(LocalDate date) {
		
		return orderEntityRepository.sumSalesByDate(date);
	}

	@Override
	public Long countByOrderDate(LocalDate date) {

		return  orderEntityRepository.countByOrderDate(date);
	}

	@Override
	public List<OrderResponse> findRecentOrders() {

	    Pageable pageable = PageRequest.of(0, 5);

	    return orderEntityRepository.findRecentOrders(pageable)
	            .stream()
	            .map(this::convertToResponse)
	            .collect(Collectors.toList());
	}



}
