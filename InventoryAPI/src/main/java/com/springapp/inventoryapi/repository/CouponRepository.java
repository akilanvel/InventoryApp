package com.springapp.inventoryapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springapp.inventoryapi.model.Coupon;

public interface CouponRepository extends JpaRepository<Coupon, Integer> {
	Coupon findByCouponCode(String couponCode);
}
