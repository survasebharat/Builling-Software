package com.nt.repository;

import java.awt.print.Pageable;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.hibernate.type.descriptor.converter.spi.JpaAttributeConverter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.nt.entity.OrderEntity;
import com.nt.io.OrderResponse;

@Repository
public interface OrderEntityRepository extends JpaRepository<OrderEntity, Long>{

	Optional<OrderEntity> findByOrderId(String orderId);
	
	List<OrderEntity>	findAllByOrderByCreatedAtDesc();
	@Query("SELECT SUM(o.grandTotal) from OrderEntity o where DATE(o.createdAt) =: date")
	Double sumSalesByDate(@Param("date") LocalDate date);
	
	@Query("SELECT COUNT(0) FROM OrderEntity o WHERE DATE(0.createdAt) =: date")
	Long countByOrderDate(@Param("date") LocalDate date);
	
	@Query("SELECT o FROM OrderEntity o ORDER BY o.createdAt DESC")
	List<OrderEntity> findRecentOrders(Pageable pageable);

}
