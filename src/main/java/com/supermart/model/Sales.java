package com.supermart.model;

public class Sales {
	private int order_id;
	private int product_id;
	private int quantity_sold;
	private String customer_name;
	private String served_by;
	
	public Sales() {
	}
	
	public Sales(int order_id, int product_id, int quantity_sold, String customer_name, String served_by) {
		this.order_id = order_id;
		this.product_id = product_id;
		this.quantity_sold = quantity_sold;
		this.customer_name = customer_name;
		this.served_by = served_by;
	}

	public int getOrder_id() {
		return order_id;
	}

	public void setOrder_id(int order_id) {
		this.order_id = order_id;
	}

	public int getProduct_id() {
		return product_id;
	}

	public void setProduct_id(int product_id) {
		this.product_id = product_id;
	}

	public int getQuantity_sold() {
		return quantity_sold;
	}

	public void setQuantity_sold(int quantity_sold) {
		this.quantity_sold = quantity_sold;
	}

	public String getCustomer_name() {
		return customer_name;
	}

	public void setCustomer_name(String customer_name) {
		this.customer_name = customer_name;
	}

	public String getServed_by() {
		return served_by;
	}

	public void setServed_by(String served_by) {
		this.served_by = served_by;
	}
	
}
