package com.supermart.service;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.supermart.dbcon.DbConnection;
import com.supermart.dbcon.DbUtil;
import com.supermart.model.Purchase;
import com.supermart.model.Sales;
import com.supermart.model.Stock;
import com.supermart.model.Supplier;
import com.supermart.model.User;

public class SupermartService {
	private Connection connection;
	private PreparedStatement statement;
	
	public SupermartService() {
	}
	
	public static int registerUser(User user) throws ClassNotFoundException {
		String query = "INSERT INTO users (user_name,password) VALUES (?, ?);";
		int insertResult = 0;
		
		try{
			Connection con = DbConnection.connectDB();
	
			PreparedStatement statement = con.prepareStatement(query);
			statement.setString(1, user.getUserName());
			statement.setString(2, user.getPassword());
			
			insertResult = statement.executeUpdate();		
			
		}catch(Exception e){
			System.out.println(e);
			System.exit(0);
		}
		return insertResult;
	}
	
	public void loginUser(User user) throws SQLException {
		String query = "SELECT * FROM users WHERE user_name=? AND password=?";
		ResultSet resultSet = null;
		//boolean userExist = false;
		try {
			connection = DbConnection.connectDB();
			statement = connection.prepareStatement(query);
			statement.setString(1, user.getUserName());
			statement.setString(2, user.getPassword());
			resultSet = statement.executeQuery();
			
			if(resultSet.next()) {
				//userExist = true;
				System.out.println("Welcome "+user.getUserName());
				System.out.println("");
			}else {
				System.out.println("Incorrect username/password");
				System.exit(0);
			}
		}finally {
			DbUtil.close(resultSet);
			DbUtil.close(statement);
			DbUtil.close(connection);
		}
		//return userExist;
	}
	
	
	
	
	public static int addPurchaseToStock(Purchase purchase) throws SQLException, ClassNotFoundException {
		String query = "SELECT * FROM stock WHERE product_id=?";
		Stock stock = null;
		int updateResult = 0;
		int quantity_available, quantity_bought, total_quantity;
		ResultSet resultSet = null;
		try {
			Connection connection = DbConnection.connectDB();
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setInt(1, purchase.getProduct_id());
			resultSet = statement.executeQuery();
			
			if(resultSet.next()) {
				stock = new Stock();
				quantity_available = resultSet.getInt("quantity_available");
				quantity_bought = purchase.getQuantity_bought();
				total_quantity = quantity_available + quantity_bought;
				stock.setStock_id(resultSet.getInt("stock_id"));
				stock.setProduct_id(resultSet.getInt("product_id"));
				stock.setQuantity_available(total_quantity);
				stock.setSelling_price(resultSet.getDouble("selling_price"));
				
				if(quantity_available==0 || quantity_bought==0 || total_quantity==0) {
					System.out.println("Unable to increment stock");
					System.exit(0);
				}else {
					try {
						String query1 = "UPDATE stock SET quantity_available=? WHERE product_id=?;";
						statement = connection.prepareStatement(query1);
						statement.setInt(1, stock.getQuantity_available());
						statement.setInt(2, purchase.getProduct_id());
						updateResult = statement.executeUpdate();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			}else {
				//add a new stock
			}
		}catch(Exception e){
			System.out.println(e);
			System.exit(0);
		}
		return updateResult;
	}
	
	public static int deductSaleFromStock(Sales sales) throws SQLException, ClassNotFoundException {
		String query = "SELECT * FROM stock WHERE product_id=?";
		Stock stock = null;
		int updateResult = 0;
		int quantity_available, quantity_sold, total_quantity;
		ResultSet resultSet = null;
		try {
			Connection connection = DbConnection.connectDB();
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setInt(1, sales.getProduct_id());
			resultSet = statement.executeQuery();
			
			if(resultSet.next()) {
				stock = new Stock();
				quantity_available = resultSet.getInt("quantity_available");
				quantity_sold = sales.getQuantity_sold();
				total_quantity = quantity_available - quantity_sold;
				stock.setStock_id(resultSet.getInt("stock_id"));
				stock.setProduct_id(resultSet.getInt("product_id"));
				stock.setQuantity_available(total_quantity);
				stock.setSelling_price(resultSet.getDouble("selling_price"));
				
				if(quantity_available==0 || quantity_sold==0 || total_quantity==0) {
					System.out.println("Unable to decrement stock");
					System.exit(0);
				}else if(total_quantity<0) {
					System.out.println("Insufficient stock to perform a sale");
				}else {
					try {
						String query1 = "UPDATE stock SET quantity_available=? WHERE product_id=?;";
						statement = connection.prepareStatement(query1);
						statement.setInt(1, stock.getQuantity_available());
						statement.setInt(2, sales.getProduct_id());
						updateResult = statement.executeUpdate();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			}
		}catch(Exception e){
			System.out.println(e);
			System.exit(0);
		}
		return updateResult;
	}
	
	
	public static int insertPurchase(Purchase purchase) throws ClassNotFoundException {
		String query = "INSERT INTO purchase (product_id,product_name,buying_price,supplier_id,quantity_bought) VALUES (?, ?, ?, ?, ?);";
		int insertResult = 0;
		
		try{
			Connection con = DbConnection.connectDB();
	
			PreparedStatement statement = con.prepareStatement(query);
			statement.setInt(1, purchase.getProduct_id());
			statement.setString(2, purchase.getProduct_name());
			statement.setDouble(3, purchase.getBuying_price());
			statement.setInt(4, purchase.getSupplier_id());
			statement.setInt(5, purchase.getQuantity_bought());
			
			addPurchaseToStock(purchase);
			insertResult = statement.executeUpdate();
			
			System.out.println("A new purchase added");
		}catch(Exception e){
			System.out.println(e);
			System.exit(0);
		}
		return insertResult;
	}
	
	public static int insertSales(Sales sales) throws ClassNotFoundException {
		String query = "INSERT INTO sales (product_id,quantity_sold,customer_name,served_by) VALUES (?, ?, ?, ?);";
		int insertResult = 0;
		
		try{
			Connection con = DbConnection.connectDB();
	
			PreparedStatement statement = con.prepareStatement(query);
			statement.setInt(1, sales.getProduct_id());
			statement.setInt(2, sales.getQuantity_sold());
			statement.setString(3, sales.getCustomer_name());
			statement.setString(4, sales.getServed_by());
			
			insertResult = statement.executeUpdate();		
			System.out.println("A new sale added");
		}catch(Exception e){
			System.out.println(e);
			System.exit(0);
		}
		return insertResult;
	}
	
	public static int insertStock(Stock stock) throws ClassNotFoundException {
		String query = "INSERT INTO stock (product_id,quantity_available,selling_price) VALUES (?, ?, ?);";
		int insertResult = 0;
		
		try{
			Connection con = DbConnection.connectDB();
	
			PreparedStatement statement = con.prepareStatement(query);
			statement.setInt(1, stock.getProduct_id());
			statement.setInt(2, stock.getQuantity_available());
			statement.setDouble(3, stock.getSelling_price());
			
			insertResult = statement.executeUpdate();		
			System.out.println("A new stock added");
		}catch(Exception e){
			System.out.println(e);
			System.exit(0);
		}
		return insertResult;
	}
	
	public static int insertSupplier(Supplier supplier) throws ClassNotFoundException {
		String query = "INSERT INTO supplier (name,contact) VALUES (?, ?);";
		int insertResult = 0;
		
		try{
			Connection con = DbConnection.connectDB();
	
			PreparedStatement statement = con.prepareStatement(query);
			statement.setString(1, supplier.getName());
			statement.setString(2, supplier.getContact());
			
			insertResult = statement.executeUpdate();		
			System.out.println("A new supplier added");
		}catch(Exception e){
			System.out.println(e);
			System.exit(0);
		}
		return insertResult;
	}

	
	public static int updatePurchase(Purchase purchase) throws ClassNotFoundException {
		String query = "UPDATE purchase SET product_name=?,buying_price=?,supplier_id=?,quantity_bought=? WHERE product_id=?;";
		int insertResult = 0;
		
		try{
			Connection con = DbConnection.connectDB();
	
			PreparedStatement statement = con.prepareStatement(query);
			statement.setString(1, purchase.getProduct_name());
			statement.setDouble(2, purchase.getBuying_price());
			statement.setInt(3, purchase.getSupplier_id());
			statement.setInt(4, purchase.getQuantity_bought());
			statement.setInt(5, purchase.getProduct_id());
			
			insertResult = statement.executeUpdate();		
			System.out.println("Record updated");
		}catch(Exception e){
			System.out.println(e);
			System.exit(0);
		}
		return insertResult;
	}
	
	public static int updateSales(Sales sales) throws ClassNotFoundException {
		String query = "UPDATE sales SET product_id=?,quantity_sold=?,customer_name=?,served_by=? WHERE order_id=?;";
		int insertResult = 0;
		
		try{
			Connection con = DbConnection.connectDB();
	
			PreparedStatement statement = con.prepareStatement(query);
			statement.setInt(1, sales.getProduct_id());
			statement.setInt(2, sales.getQuantity_sold());
			statement.setString(3, sales.getCustomer_name());
			statement.setString(4, sales.getServed_by());
			statement.setInt(5, sales.getOrder_id());
			
			insertResult = statement.executeUpdate();		
			System.out.println("Record updated");
		}catch(Exception e){
			System.out.println(e);
			System.exit(0);
		}
		return insertResult;
	}
	
	public static int updateStock(Stock stock) throws ClassNotFoundException {
		String query = "UPDATE stock SET product_id=?,quantity_available=?,selling_price=? WHERE stock_id=?;";
		int insertResult = 0;
		
		try{
			Connection con = DbConnection.connectDB();
	
			PreparedStatement statement = con.prepareStatement(query);
			statement.setInt(1, stock.getProduct_id());
			statement.setInt(2, stock.getQuantity_available());
			statement.setDouble(3, stock.getSelling_price());
			statement.setInt(4, stock.getStock_id());
			
			insertResult = statement.executeUpdate();		
			System.out.println("Record updated");
		}catch(Exception e){
			System.out.println(e);
			System.exit(0);
		}
		return insertResult;
	}
	
	public static int updateSupplier(Supplier supplier) throws ClassNotFoundException {
		String query = "UPDATE supplier SET name=?,contact=? WHERE supplier_id=?;";
		int insertResult = 0;
		
		try{
			Connection con = DbConnection.connectDB();
	
			PreparedStatement statement = con.prepareStatement(query);
			statement.setString(1, supplier.getName());
			statement.setString(2, supplier.getContact());
			statement.setInt(3, supplier.getSupplier_id());
			
			insertResult = statement.executeUpdate();		
			System.out.println("Record updated");
		}catch(Exception e){
			System.out.println(e);
			System.exit(0);
		}
		return insertResult;
	}

	
	public List<Purchase> getAllPurchases() throws SQLException {
		String query = "SELECT * FROM purchase";
		List<Purchase> list = new ArrayList<Purchase>();
		Purchase purchase = null;
		ResultSet resultSet = null;
		try {
			connection = DbConnection.connectDB();
			statement = connection.prepareStatement(query);
			resultSet = statement.executeQuery();
			
			while(resultSet.next()) {
				purchase = new Purchase();
				/*Retrieve one employee details 
                and store it in employee object*/
				purchase.setProduct_id(resultSet.getInt("product_id"));
				purchase.setProduct_name(resultSet.getString("product_name"));
				purchase.setBuying_price(resultSet.getDouble("buying_price"));
				purchase.setSupplier_id(resultSet.getInt("supplier_id"));
				purchase.setQuantity_bought(resultSet.getInt("quantity_bought"));
				
				System.out.println("product_id: "+purchase.getProduct_id());
				System.out.println("product_name: "+purchase.getProduct_name());
				System.out.println("buying_price: "+purchase.getBuying_price());
				System.out.println("supplier_id: "+purchase.getSupplier_id());
				System.out.println("quantity_bought: "+purchase.getQuantity_bought());
				System.out.println("");
				
				list.add(purchase);
			}
		}finally {
			DbUtil.close(resultSet);
			DbUtil.close(statement);
			DbUtil.close(connection);
		}
		return list;
	}
	
	public List<Sales> getAllSales() throws SQLException {
		String query = "SELECT * FROM sales";
		List<Sales> list = new ArrayList<Sales>();
		Sales sales = null;
		ResultSet resultSet = null;
		try {
			connection = DbConnection.connectDB();
			statement = connection.prepareStatement(query);
			resultSet = statement.executeQuery();
			
			while(resultSet.next()) {
				sales = new Sales();
				sales.setProduct_id(resultSet.getInt("product_id"));
				sales.setQuantity_sold(resultSet.getInt("quantity_sold"));
				sales.setCustomer_name(resultSet.getString("customer_name"));
				sales.setServed_by(resultSet.getString("served_by"));
				
				System.out.println("product_id: "+sales.getProduct_id());
				System.out.println("quantity_sold: "+sales.getQuantity_sold());
				System.out.println("customer_name: "+sales.getCustomer_name());
				System.out.println("served_by: "+sales.getServed_by());
				System.out.println("");
				
				list.add(sales);
			}
		}finally {
			DbUtil.close(resultSet);
			DbUtil.close(statement);
			DbUtil.close(connection);
		}
		return list;
	}
	
	public List<Stock> getAllStock() throws SQLException {
		String query = "SELECT * FROM stock";
		List<Stock> list = new ArrayList<Stock>();
		Stock stock = null;
		ResultSet resultSet = null;
		try {
			connection = DbConnection.connectDB();
			statement = connection.prepareStatement(query);
			resultSet = statement.executeQuery();
			
			while(resultSet.next()) {
				stock = new Stock();
				stock.setProduct_id(resultSet.getInt("product_id"));
				stock.setQuantity_available(resultSet.getInt("quantity_available"));
				stock.setSelling_price(resultSet.getDouble("selling_price"));
				
				System.out.println("product_id: "+stock.getProduct_id());
				System.out.println("quantity_available: "+stock.getQuantity_available());
				System.out.println("selling_price: "+stock.getSelling_price());
				System.out.println("");
				
				list.add(stock);
			}
		}finally {
			DbUtil.close(resultSet);
			DbUtil.close(statement);
			DbUtil.close(connection);
		}
		return list;
	}
	
	public List<Supplier> getAllSuppliers() throws SQLException {
		String query = "SELECT * FROM supplier";
		List<Supplier> list = new ArrayList<Supplier>();
		Supplier supplier = null;
		ResultSet resultSet = null;
		try {
			connection = DbConnection.connectDB();
			statement = connection.prepareStatement(query);
			resultSet = statement.executeQuery();
			
			while(resultSet.next()) {
				supplier = new Supplier();
				supplier.setSupplier_id(resultSet.getInt("supplier_id"));
				supplier.setName(resultSet.getString("name"));
				supplier.setContact(resultSet.getString("contact"));
				
				System.out.println("supplier_id: "+supplier.getSupplier_id());
				System.out.println("name: "+supplier.getName());
				System.out.println("contact: "+supplier.getContact());
				System.out.println("");
				
				list.add(supplier);
			}
		}finally {
			DbUtil.close(resultSet);
			DbUtil.close(statement);
			DbUtil.close(connection);
		}
		return list;
	}
	
	
	public Supplier getSupplier(int supplierId) throws SQLException {
		String query = "SELECT * FROM supplier WHERE supplier_id="+supplierId;
		Supplier supplier = null;
		ResultSet resultSet = null;
		try {
			connection = DbConnection.connectDB();
			statement = connection.prepareStatement(query);
			resultSet = statement.executeQuery();
			
			if(resultSet.next()) {
				supplier = new Supplier();
				supplier.setSupplier_id(resultSet.getInt("supplier_id"));
				supplier.setName(resultSet.getString("name"));
				supplier.setContact(resultSet.getString("contact"));
				System.out.println("");
			}
		}finally {
			DbUtil.close(resultSet);
			DbUtil.close(statement);
			DbUtil.close(connection);
		}
		return supplier;
	}
	
	
	
	/*
	 * public void view() { try { String a = getPurchase(1).getProduct_name(); int b
	 * = getSale(1).getOrder_id();
	 * 
	 * System.out.println(a + b); } catch (SQLException e) { // TODO Auto-generated
	 * catch block e.printStackTrace(); } }
	 */
	
	
	
	public Sales getSale(int saleId) throws SQLException {
		String query = "SELECT * FROM sales WHERE order_id="+saleId;
		Sales sale = null;
		ResultSet resultSet = null;
		try {
			connection = DbConnection.connectDB();
			statement = connection.prepareStatement(query);
			resultSet = statement.executeQuery();
			
			if(resultSet.next()) {
				sale = new Sales();
				sale.setOrder_id(resultSet.getInt("order_id"));
				sale.setProduct_id(resultSet.getInt("product_id"));
				sale.setQuantity_sold(resultSet.getInt("quantity_sold"));
				sale.setCustomer_name(resultSet.getString("customer_name"));
				sale.setServed_by(resultSet.getString("served_by"));
			}
		}finally {
			DbUtil.close(resultSet);
			DbUtil.close(statement);
			DbUtil.close(connection);
		}
		return sale;
	}
	
	public Stock getStock(int stockId) throws SQLException {
		String query = "SELECT * FROM stock WHERE stock_id="+stockId;
		Stock stock = null;
		ResultSet resultSet = null;
		try {
			connection = DbConnection.connectDB();
			statement = connection.prepareStatement(query);
			resultSet = statement.executeQuery();
			
			if(resultSet.next()) {
				stock = new Stock();
				stock.setStock_id(resultSet.getInt("stock_id"));
				stock.setProduct_id(resultSet.getInt("product_id"));
				stock.setQuantity_available(resultSet.getInt("quantity_available"));
				stock.setSelling_price(resultSet.getDouble("selling_price"));
			}
		}finally {
			DbUtil.close(resultSet);
			DbUtil.close(statement);
			DbUtil.close(connection);
		}
		return stock;
	}
	
	public Purchase getPurchase(int purchaseId) throws SQLException {
		String query = "SELECT * FROM purchase WHERE product_id="+purchaseId;
		Purchase purchase = null;
		ResultSet resultSet = null;
		try {
			connection = DbConnection.connectDB();
			statement = connection.prepareStatement(query);
			resultSet = statement.executeQuery(query);
			
			if(resultSet.next()) {
				purchase = new Purchase();
				purchase.setProduct_id(resultSet.getInt("product_id"));
				purchase.setProduct_name(resultSet.getString("product_name"));
				purchase.setBuying_price(resultSet.getDouble("buying_price"));
				purchase.setSupplier_id(resultSet.getInt("supplier_id"));
				purchase.setQuantity_bought(resultSet.getInt("quantity_bought"));
			}
		}finally {
			DbUtil.close(resultSet);
			DbUtil.close(statement);
			DbUtil.close(connection);
		}
		return purchase;
	}
	
	
	public static int deletePurchase(int product_id) throws ClassNotFoundException {
		String query = "DELETE FROM purchase WHERE product_id=?";
		int insertResult = 0;
		
		try{
			Connection con = DbConnection.connectDB();
			PreparedStatement statement = con.prepareStatement(query);
			statement.setInt(1, product_id);
			insertResult = statement.executeUpdate();		
			
			System.out.println("Record deleted!");
		}catch(Exception e){
			System.out.println(e);
			System.exit(0);
		}
		return insertResult;
	}
	
	public static int deleteSale(int order_id) throws ClassNotFoundException {
		String query = "DELETE FROM sales WHERE order_id=?";
		int insertResult = 0;
		
		try{
			Connection con = DbConnection.connectDB();
			PreparedStatement statement = con.prepareStatement(query);
			
			insertResult = statement.executeUpdate();		
			
			System.out.println("Record deleted!");
		}catch(Exception e){
			System.out.println(e);
			System.exit(0);
		}
		return insertResult;
	}
	
	public static int deleteStock(int stock_id) throws ClassNotFoundException {
		String query = "DELETE FROM stock WHERE product_id="+stock_id+";";
		int insertResult = 0;
		
		try{
			Connection con = DbConnection.connectDB();
			PreparedStatement statement = con.prepareStatement(query);
			insertResult = statement.executeUpdate();		
			
			System.out.println("Record deleted!");
		}catch(Exception e){
			System.out.println(e);
			System.exit(0);
		}
		return insertResult;
	}
	
	public static int deleteSupplier(int supplier_id) throws ClassNotFoundException {
		String query = "DELETE FROM supplier WHERE product_id="+supplier_id+";";
		int insertResult = 0;
		
		try{
			Connection con = DbConnection.connectDB();
			PreparedStatement statement = con.prepareStatement(query);
			insertResult = statement.executeUpdate();		
			
			System.out.println("Record deleted!");
		}catch(Exception e){
			System.out.println(e);
			System.exit(0);
		}
		return insertResult;
	}
	
	
	
	
	
	
	
	public HashMap<Purchase, Supplier> purchaseView(int supplier_id) throws SQLException {
		String query = "SELECT purchase.supplier_id,purchase.product_name,supplier.supplier_id,supplier.name FROM purchase,supplier WHERE purchase.supplier_id=supplier.supplier_id="+supplier_id;
		
		HashMap<Purchase, Supplier> map = new HashMap<>();
		Purchase purchase = null;
		Supplier supplier = null;
		ResultSet resultSet = null;
		try {
			connection = DbConnection.connectDB();
			statement = connection.prepareStatement(query);
			resultSet = statement.executeQuery();
			
			if(resultSet.next()) {
				purchase = new Purchase();
				supplier = new Supplier();
				purchase.setSupplier_id(resultSet.getInt("supplier_id"));
				//purchase.setProduct_id(resultSet.getInt("product_id"));
				purchase.setProduct_name(resultSet.getString("product_name"));
				//purchase.setBuying_price(resultSet.getDouble("buying_price"));
				supplier.setSupplier_id(resultSet.getInt("supplier_id"));
				supplier.setName(resultSet.getString("name"));
				//purchase.setQuantity_bought(resultSet.getInt("quantity_bought"));
				
				map.put(purchase, supplier);
			}
		}finally {
			DbUtil.close(resultSet);
			DbUtil.close(statement);
			DbUtil.close(connection);
		}
		return map;
		
	}
}
