package com.supermart.model;

public class Purchase {
	private int product_id;
	private String product_name;
	private double buying_price;
	private int supplier_id;
	private int quantity_bought;
	
	public Purchase() {
	}
	
	public Purchase(int product_id, String product_name, double buying_price, int supplier_id, int quantity_bought) {
		this.product_id = product_id;
		this.product_name = product_name;
		this.buying_price = buying_price;
		this.supplier_id = supplier_id;
		this.quantity_bought = quantity_bought;
	}

	public int getProduct_id() {
		return product_id;
	}

	public void setProduct_id(int product_id) {
		this.product_id = product_id;
	}

	public String getProduct_name() {
		return product_name;
	}

	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}

	public double getBuying_price() {
		return buying_price;
	}

	public void setBuying_price(double buying_price) {
		this.buying_price = buying_price;
	}

	public int getSupplier_id() {
		return supplier_id;
	}

	public void setSupplier_id(int supplier_id) {
		this.supplier_id = supplier_id;
	}

	public int getQuantity_bought() {
		return quantity_bought;
	}

	public void setQuantity_bought(int quantity_bought) {
		this.quantity_bought = quantity_bought;
	}

}
