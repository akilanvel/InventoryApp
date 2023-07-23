package com.springapp.inventoryapi.service;

import java.time.LocalDate;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springapp.inventoryapi.enums.OrderStatus;
import com.springapp.inventoryapi.model.InwardRegister;
import com.springapp.inventoryapi.model.Order;
import com.springapp.inventoryapi.repository.InwardRegisterRepository;

@Service
public class InwardRegisterService {

	@Autowired
	private InwardRegisterRepository inwardRegisterRepository;

	public void insert(Order order) {
		InwardRegister ir = new InwardRegister();
		ir.setInvoice("IV-" + order.getSupplier().getId() + "-" + (int) (Math.random() * 1000));
		ir.setQuantity(order.getQuantity());
		ir.setDateOfSupply(LocalDate.now());
		ir.setStatus(OrderStatus.RECEIVED);
		ir.setProduct(order.getProduct());
		ir.setSupplier(order.getSupplier());
		ir.setWarehouse(order.getWarehouse());
		inwardRegisterRepository.save(ir);
	}
}
