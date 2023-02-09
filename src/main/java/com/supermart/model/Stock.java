package com.supermart.model;

public class Stock {
	private int stock_id;
	private int product_id;
	private int quantity_available;
	private double selling_price;
	
	public Stock() {
	}
	
	public Stock(int stock_id, int product_id, int quantity_available, double selling_price) {
		super();
		this.stock_id = stock_id;
		this.product_id = product_id;
		this.quantity_available = quantity_available;
		this.selling_price = selling_price;
	}

	public int getStock_id() {
		return stock_id;
	}

	public void setStock_id(int stock_id) {
		this.stock_id = stock_id;
	}

	public int getProduct_id() {
		return product_id;
	}

	public void setProduct_id(int product_id) {
		this.product_id = product_id;
	}

	public int getQuantity_available() {
		return quantity_available;
	}

	public void setQuantity_available(int quantity_available) {
		this.quantity_available = quantity_available;
	}

	public double getSelling_price() {
		return selling_price;
	}

	public void setSelling_price(double selling_price) {
		this.selling_price = selling_price;
	}

}
