package com.springapp.inventoryapi.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "outward_register")
public class OutwardRegister {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	private String invoice;
	private int quantity;

	@Column(name = "date_of_supply")
	private LocalDate dateOfSupply;

	@Column(name = "date_of_delivery")
	private LocalDate dateOfDelivery;

	@OneToOne
	private Warehouse warehouse;

	@OneToOne
	private Customer customer;

	@OneToOne
	private Product product;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getInvoiceNo() {
		return invoice;
	}

	public void setInvoiceNo(String invoiceNo) {
		this.invoice = invoiceNo;
	}

	public LocalDate getDateOfSupply() {
		return dateOfSupply;
	}

	public void setDateOfSupply(LocalDate dateOfSupply) {
		this.dateOfSupply = dateOfSupply;
	}

	public LocalDate getDateOfDelivery() {
		return dateOfDelivery;
	}

	public void setDateOfDelivery(LocalDate dateOfDelivery) {
		this.dateOfDelivery = dateOfDelivery;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public Warehouse getWarehouse() {
		return warehouse;
	}

	public void setWarehouse(Warehouse warehouse) {
		this.warehouse = warehouse;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}
}
