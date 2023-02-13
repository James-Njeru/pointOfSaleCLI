package com.supermart.cli;

import java.sql.SQLException;
import java.util.Scanner;

import com.supermart.model.Purchase;
import com.supermart.model.Sales;
import com.supermart.model.Stock;
import com.supermart.model.Supplier;
import com.supermart.model.User;
import com.supermart.service.SupermartService;

public class Cli {
	
	public static void loopInput() {
		String input;
		while(true) {
			System.out.println("Do you have an account?");
			System.out.println("Enter 'y' for yes or 'n' for no: ");
	    	Scanner kb = new Scanner(System.in);
	    	input = (kb.nextLine()).toLowerCase();
	    	//kb.close();
	    	if(input.equals("y")) {
	    		System.out.println("");
	    		login();
	    		break;
	    	}else if(input.equals("n")) {
	    		System.out.println("");
	    		register();
	    		break;
	    	}else {
	    		System.out.println("Invalid input. Please try again!");
	    		System.out.println("");
	    	}
		}
	}
	
	public static void register(){
		String name,pwd,confirmPwd;
		User user = new User();
		SupermartService supermartService = new SupermartService();
		
    	System.out.println("Register account");
    	Scanner kb = new Scanner(System.in);
    	System.out.println("Enter Username: ");
    	name = kb.nextLine();
        
        System.out.println("Enter Password: ");
        pwd = kb.nextLine();
        
        System.out.println("Confirm Password: ");
        confirmPwd = kb.nextLine();
        //kb.close();
        System.out.println("");
        
        if(name.isEmpty() || pwd.isEmpty() || confirmPwd.isEmpty()) {
        	System.out.println("Fields cannot be empty");
        	exitSystem();
        }else if(!pwd.equals(confirmPwd)){
        	System.out.println("Password should be same as confirm password!");
        	exitSystem();
        }else if(pwd.equals(confirmPwd)) {
        	user.setUserName(name);
        	user.setPassword(pwd);
        	
        	try {
        		SupermartService.registerUser(user);
				System.out.println("Account created. Please login.");
				System.out.println("");
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
        }else {
        	System.out.println("Account not created.");
        }
	}
	
	public static void login(){
		String name,pwd;
		User user = new User();
		SupermartService supermartService = new SupermartService();
		
    	System.out.println("Login");
    	Scanner kb = new Scanner(System.in);
    	System.out.println("Enter Username: ");
    	name = kb.nextLine();
        
        System.out.println("Enter Password: ");
        pwd = kb.nextLine();
    	
		System.out.println("");
		
		if(name.isEmpty() || pwd.isEmpty()) {
        	System.out.println("Fields cannot be empty");
        	exitSystem();
        }else {
        	user.setUserName(name);
        	user.setPassword(pwd);
        	try {
				supermartService.loginUser(user);
				
				String entry;
				while(true) {
					System.out.println("");
					System.out.println("Enter 'ap' to add a purchase, 'as' to add a sale, 'ao' to add stock, 'au' to add a supplier,\n"
							+ "'up' to update a purchase, 'us' to update a sale, 'uo' to update stock, 'uu' to update a supplier,\n"
							+ "'gp' to get all purchase, 'gs' to get all sales, 'go' to get all stock, 'gu' to get all suppliers,\n"
							+ "'dp' to delete a purchase, 'ds' to delete a sale, 'do' to delete stock, 'du' to delete a supplier.\n"
							+ "Enter 'q' to quit");
					System.out.println("");
					System.out.println("Enter your selection: ");
					entry = (kb.nextLine()).toLowerCase();
					System.out.println("");
					if(entry.equals("ap")) {
						addPurchase();
						//break;
					}else if(entry.equals("as")) {
						addSales();
						//break;
					}else if(entry.equals("ao")) {
						addStock();
						//break;
					}
					else if(entry.equals("au")) {
						addSupplier();
						//break;
					}else if(entry.equals("up")) {
						editPurchase();
						//break;
					}else if(entry.equals("us")) {
						editSales();
						//break;
					}else if(entry.equals("uo")) {
						editStock();
						//break;
					}else if(entry.equals("uu")) {
						editSupplier();
						//break;
					}else if(entry.equals("gp")) {
						supermartService.getAllPurchases();
						//break;
					}else if(entry.equals("gs")) {
						supermartService.getAllSales();
						//break;
					}else if(entry.equals("go")) {
						supermartService.getAllStock();
						//break;
					}else if(entry.equals("gu")) {
						supermartService.getAllSuppliers();
						//break;
					}else if(entry.equals("dp")) {
						deletePurchase();
						//break;
					}else if(entry.equals("ds")) {
						deleteSales();
						//break;
					}else if(entry.equals("do")) {
						deleteStock();
						//break;
					}else if(entry.equals("du")) {
						deleteSupplier();
						//break;
					}else if(entry.equals("q")){
						exitSystem();
					}else {
						System.out.println("Invalid input. Please try again!");
						System.out.println("");
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
        }
		//kb.close();
	}
	
	private static void deleteSupplier() {
		System.out.println("Delete a supplier");
    	Scanner kb = new Scanner(System.in);
    	System.out.println("Enter supplier_id: ");
    	int supplier_id = Integer.parseInt(kb.nextLine());
    	//kb.close();
    	
    	try {
			SupermartService.deleteSupplier(supplier_id);
			System.out.println("Record deleted");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	private static void deleteStock() {
		System.out.println("Delete stock");
    	Scanner kb = new Scanner(System.in);
    	System.out.println("Enter stock_id: ");
    	int stock_id = Integer.parseInt(kb.nextLine());
    	//kb.close();
    	
    	try {
			SupermartService.deleteStock(stock_id);
			System.out.println("Record deleted");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	private static void deleteSales() {
		System.out.println("Delete a sale");
    	Scanner kb = new Scanner(System.in);
    	System.out.println("Enter order_id: ");
    	int order_id = Integer.parseInt(kb.nextLine());
    	//kb.close();
    	
    	try {
			SupermartService.deleteSale(order_id);
			System.out.println("Record deleted");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	private static void deletePurchase() {
		int product_id;
		System.out.println("Delete a purchase");
    	Scanner kb = new Scanner(System.in);
    	System.out.println("Enter product_id: ");
    	product_id = Integer.parseInt(kb.nextLine());
    	//kb.close();
    	
    	try {
			SupermartService.deletePurchase(product_id);
			System.out.println("Record deleted");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	private static void editSupplier() {
		String name,contact;
		int supplier_id = 0;
		Supplier supplier = new Supplier();
		
    	System.out.println("Edit a supplier");
    	Scanner kb = new Scanner(System.in);
    	System.out.println("Enter supplier_id: ");
    	supplier_id = Integer.parseInt(kb.nextLine());
    	
    	System.out.println("supplier name: ");
    	name = kb.nextLine();
        
        System.out.println("Enter supplier contact: ");
        contact = kb.nextLine();
        //kb.close();
        System.out.println("");
        
        if(name.isEmpty() || contact.isEmpty() || supplier_id==0) {
        	System.out.println("Fields cannot be empty");
        	exitSystem();
        }else {
        	supplier.setSupplier_id(supplier_id);
        	supplier.setName(name);
        	supplier.setContact(contact);
        	try {
				SupermartService.updateSupplier(supplier);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
        }
	}

	private static void editStock() {
		double selling_price = 0;
		int product_id,stock_id,quantity_available = 0;
		Stock stock = new Stock();
		
    	System.out.println("Edit stock");
    	Scanner kb = new Scanner(System.in);
    	System.out.println("Enter stock_id: ");
    	stock_id = Integer.parseInt(kb.nextLine());
        
        System.out.println("Enter selling_price: ");
        selling_price = Double.parseDouble(kb.nextLine());
        
        System.out.println("Enter product_id: ");
        product_id = Integer.parseInt(kb.nextLine());
        
        System.out.println("Enter quantity_available: ");
        quantity_available = Integer.parseInt(kb.nextLine());
        //kb.close();
        System.out.println("");
        
        if(product_id==0 || stock_id==0 || selling_price==0 || quantity_available==0) {
        	System.out.println("Fields cannot be empty");
        	exitSystem();
        }else {
        	stock.setSelling_price(selling_price);
        	stock.setProduct_id(product_id);
        	stock.setStock_id(stock_id);
        	stock.setQuantity_available(quantity_available);
        	try {
				SupermartService.updateStock(stock);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
        }
	}

	private static void editSales() {
		String customer_name,served_by;
		int product_id,order_id,quantity_sold = 0;
		Sales sales = new Sales();
		
    	System.out.println("Edit a sale");
    	Scanner kb = new Scanner(System.in);
    	System.out.println("Enter order_id: ");
    	order_id = Integer.parseInt(kb.nextLine());
    	
    	System.out.println("Enter customer_name: ");
    	customer_name = kb.nextLine();
        
        System.out.println("Enter served_by: ");
        served_by = kb.nextLine();
        
        System.out.println("Enter product_id: ");
        product_id = Integer.parseInt(kb.nextLine());
        
        System.out.println("Enter quantity_sold: ");
        quantity_sold = Integer.parseInt(kb.nextLine());
        //kb.close();
        System.out.println("");
        
        if(product_id==0 || customer_name.isEmpty() || order_id==0 || served_by.isEmpty() || quantity_sold==0) {
        	System.out.println("Fields cannot be empty");
        	exitSystem();
        }else {
        	sales.setCustomer_name(customer_name);
        	sales.setServed_by(served_by);
        	sales.setProduct_id(product_id);
        	sales.setOrder_id(order_id);
        	sales.setQuantity_sold(quantity_sold);
        	try {
				SupermartService.updateSales(sales);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
        }
	}
	
	public static void editPurchase(){
		String product_name;
		double buying_price = 0;
		int product_id,supplier_id,quantity_bought = 0;
		Purchase purchase = new Purchase();
		
    	System.out.println("Edit a purchase");
    	Scanner kb = new Scanner(System.in);
    	System.out.println("Enter product_id: ");
    	product_id = Integer.parseInt(kb.nextLine());
    	
    	System.out.println("Enter product_name: ");
    	product_name = kb.nextLine();
        
        System.out.println("Enter buying_price: ");
        buying_price = Double.parseDouble(kb.nextLine());
        
        System.out.println("Enter supplier_id: ");
        supplier_id = Integer.parseInt(kb.nextLine());
        
        System.out.println("Enter quantity_bought: ");
        quantity_bought = Integer.parseInt(kb.nextLine());
        //kb.close();
        System.out.println("");
        
        if(product_id==0 || product_name.isEmpty() || buying_price==0 || supplier_id==0 || quantity_bought==0) {
        	System.out.println("Fields cannot be empty");
        	exitSystem();
        }else {
        	purchase.setProduct_id(product_id);
        	purchase.setProduct_name(product_name);
        	purchase.setBuying_price(buying_price);
        	purchase.setSupplier_id(supplier_id);
        	purchase.setQuantity_bought(quantity_bought);
        	try {
				SupermartService.updatePurchase(purchase);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
        }
	}

	public static void addPurchase(){
		String product_name;
		double buying_price = 0;
		int product_id,supplier_id,quantity_bought = 0;
		Purchase purchase = new Purchase();
		
    	System.out.println("Add a new purchase");
    	Scanner kb = new Scanner(System.in);
    	System.out.println("Enter product_id: ");
    	product_id = Integer.parseInt(kb.nextLine());
    	
    	System.out.println("Enter product_name: ");
    	product_name = kb.nextLine();
        
        System.out.println("Enter buying_price: ");
        buying_price = Double.parseDouble(kb.nextLine());
        
        System.out.println("Enter supplier_id: ");
        supplier_id = Integer.parseInt(kb.nextLine());
        
        System.out.println("Enter quantity_bought: ");
        quantity_bought = Integer.parseInt(kb.nextLine());
        //kb.close();
        System.out.println("");
        
        if(product_id==0 || product_name.isEmpty() || buying_price==0 || supplier_id==0 || quantity_bought==0) {
        	System.out.println("Fields cannot be empty");
        	exitSystem();
        }else {
        	purchase.setProduct_id(product_id);
        	purchase.setProduct_name(product_name);
        	purchase.setBuying_price(buying_price);
        	purchase.setSupplier_id(supplier_id);
        	purchase.setQuantity_bought(quantity_bought);
        	try {
				SupermartService.insertPurchase(purchase);
        		//SupermartService.insertTrial(purchase.getQuantity_bought());
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
        }
	}
	
	public static void addSales(){
		int product_id,quantity_sold = 0;
		String customer_name,served_by;
		Sales sales = new Sales();
		
    	System.out.println("Add a new sale");
    	Scanner kb = new Scanner(System.in);
    	System.out.println("Enter product_id: ");
    	product_id = Integer.parseInt(kb.nextLine());
        
        System.out.println("Enter quantity_sold: ");
        quantity_sold = Integer.parseInt(kb.nextLine());
        
        System.out.println("Enter customer_name: ");
        customer_name = kb.nextLine();
        
        System.out.println("Enter served_by: ");
        served_by = kb.nextLine();
        //kb.close();
        System.out.println("");
        
        if(product_id==0 || quantity_sold==0 || customer_name.isEmpty() || served_by.isEmpty()) {
        	System.out.println("Fields cannot be empty");
        	exitSystem();
        }else {
        	sales.setProduct_id(product_id);
        	sales.setQuantity_sold(quantity_sold);
        	sales.setCustomer_name(customer_name);
        	sales.setServed_by(served_by);
        	try {
				SupermartService.insertSales(sales);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
        }
	}
	
	public static void addStock(){
		int product_id,quantity_available = 0;
		double selling_price = 0;
		Stock stock = new Stock();
		
    	System.out.println("Add a new stock");
    	Scanner kb = new Scanner(System.in);
    	System.out.println("Enter product_id: ");
    	product_id = Integer.parseInt(kb.nextLine());
        
        System.out.println("Enter quantity_available: ");
        quantity_available = Integer.parseInt(kb.nextLine());
        
        System.out.println("Enter selling_price: ");
        selling_price = Double.parseDouble(kb.nextLine());
        //kb.close();
        System.out.println("");
        
        if(product_id==0 || quantity_available==0 || selling_price==0) {
        	System.out.println("Fields cannot be empty");
        	exitSystem();
        }else {
        	stock.setProduct_id(product_id);
        	stock.setQuantity_available(quantity_available);
        	stock.setSelling_price(selling_price);
        	try {
				SupermartService.insertStock(stock);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
        }
	}
	
	public static void addSupplier(){
		String name,contact;
		Supplier supplier = new Supplier();
		
    	System.out.println("Add a new supplier");
    	Scanner kb = new Scanner(System.in);
    	System.out.println("Enter supplier name: ");
    	name = kb.nextLine();
        
        System.out.println("Enter supplier contact: ");
        contact = kb.nextLine();
        //kb.close();
        System.out.println("");
        
        if(name.isEmpty() || contact.isEmpty()) {
        	System.out.println("Fields cannot be empty");
        	exitSystem();
        }else {
        	supplier.setName(name);
        	supplier.setContact(contact);
        	try {
				SupermartService.insertSupplier(supplier);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
        }
	}
	
	

	public static void exitSystem(){
		System.out.println("//////////////////////////////////////////");
	    System.out.println("You have exited the system");
	    System.exit(0);
	}
}
