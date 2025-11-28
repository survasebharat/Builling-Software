package com.nt.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nt.entity.OrderItemEntity;

public interface OrderItemEntityRepository extends JpaRepository<OrderItemEntity, Long>{

}
