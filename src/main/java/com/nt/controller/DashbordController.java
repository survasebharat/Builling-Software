package com.nt.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nt.io.DashboardResponse;
import com.nt.io.OrderResponse;
import com.nt.service.OrderService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/dashboard")
@RequiredArgsConstructor
public class DashbordController {

	private final OrderService orderService;
	
	@GetMapping
	public DashboardResponse getDashBoardData()
	{
		LocalDate today = LocalDate.now();
		Double todaySale = orderService.sumSalesByDate(today);
		Long todayOrderCount =orderService.countByOrderDate(today);
		List<OrderResponse> recentOrder = orderService.findRecentOrders();
		
		return new DashboardResponse(
				todaySale != null ? todaySale: 0.0,
				todayOrderCount != null ? todayOrderCount:0,
						recentOrder
						);
	}
	
}
