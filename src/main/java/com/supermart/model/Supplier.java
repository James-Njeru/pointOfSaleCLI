package com.supermart.model;

public class Supplier {
	private int supplier_id;
	private String name;
	private String contact;
	
	public Supplier() {
	}
	
	public Supplier(int supplier_id, String name, String contact) {
		this.supplier_id = supplier_id;
		this.name = name;
		this.contact = contact;
	}
	
	public int getSupplier_id() {
		return supplier_id;
	}
	public void setSupplier_id(int supplier_id) {
		this.supplier_id = supplier_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	
	
}
