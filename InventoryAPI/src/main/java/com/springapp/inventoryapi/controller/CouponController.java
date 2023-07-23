package com.springapp.inventoryapi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springapp.inventoryapi.exception.InvalidCouponCode;
import com.springapp.inventoryapi.model.Coupon;
import com.springapp.inventoryapi.service.CouponService;

@RestController
@RequestMapping("/coupon")
@CrossOrigin(origins = { "http://localhost:3000/" })
public class CouponController {

	@Autowired
	private CouponService couponService;

	@PostMapping("/add")
	public Coupon addCoupon(@RequestBody Coupon coupon) {
		coupon = couponService.insert(coupon);
		return coupon;
	}

	@GetMapping("/all")
	public List<Coupon> getAll() {
		return couponService.getAll();
	}

	@PostMapping("/apply")
	public ResponseEntity<?> applyCoupon(@RequestBody Coupon coupon) {
		double totalAmount = coupon.getTotalAmount();
		String couponCode = coupon.getCouponCode();

		try {
			coupon = couponService.verify(totalAmount, couponCode);
			return ResponseEntity.status(HttpStatus.OK).body(coupon);
		} catch (InvalidCouponCode e) {
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(e.getMessage());
		}
	}
}
