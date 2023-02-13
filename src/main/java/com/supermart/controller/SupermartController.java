package com.supermart.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.supermart.model.Purchase;
import com.supermart.model.Sales;
import com.supermart.model.Stock;
import com.supermart.model.Supplier;
import com.supermart.service.SupermartService;

public class SupermartController extends HttpServlet {
	
		private static final long serialVersionUID = 319867896525879301L;
		
		public SupermartController()
		{
		}
		
		protected void doSetPurchase(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
			int product_id = Integer.parseInt(request.getParameter("product_id"));
			String product_name = request.getParameter("product_name");
			double buying_price = Double.parseDouble(request.getParameter("buying_price"));
			int supplier_id = Integer.parseInt(request.getParameter("supplier_id"));
			int quantity_bought = Integer.parseInt(request.getParameter("quantity_bought"));
			
			Purchase purchase = new  Purchase();
			purchase.setProduct_id(product_id);
			purchase.setProduct_name(product_name);
			purchase.setBuying_price(buying_price);
			purchase.setSupplier_id(supplier_id);
			purchase.setQuantity_bought(quantity_bought);
			
		try {
			int res = SupermartService.insertPurchase(purchase);
			if (res==0) {
				HttpSession session = request.getSession(true);
				session.setAttribute("Error", "Purchase Exist");
			} else {
				HttpSession session = request.getSession(true);
				session.setAttribute("Valid", "Purchase Entered");
			}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		protected void doSetSales(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
			int product_id = Integer.parseInt(request.getParameter("product_id"));
			int quantity_sold = Integer.parseInt(request.getParameter("quantity_sold"));
			String customer_name = request.getParameter("customer_name");
			String served_by = request.getParameter("served_by");
			
			Sales sale = new  Sales();
			sale.setProduct_id(product_id);
			sale.setQuantity_sold(quantity_sold);
			sale.setCustomer_name(customer_name);
			sale.setServed_by(served_by);
			
		try {
			int res = SupermartService.insertSales(sale);
			if (res==0) {
				HttpSession session = request.getSession(true);
				session.setAttribute("Error", "Sale Exist");
			} else {
				HttpSession session = request.getSession(true);
				session.setAttribute("Valid", "Sale Entered");
			}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		protected void doSetStock(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
			int product_id = Integer.parseInt(request.getParameter("product_id"));
			int quantity_available = Integer.parseInt(request.getParameter("quantity_available"));
			double selling_price = Double.parseDouble(request.getParameter("selling_price"));
			
			Stock stock =  new Stock();
			stock.setProduct_id(product_id);
			stock.setQuantity_available(quantity_available);
			stock.setSelling_price(selling_price);
			
		try {
			int res = SupermartService.insertStock(stock);
			if (res==0) {
				HttpSession session = request.getSession(true);
				session.setAttribute("Error", "Stock Exist");
			} else {
				HttpSession session = request.getSession(true);
				session.setAttribute("Valid", "Stock Entered");
			}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		protected void doSetSupplier(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
			String name = request.getParameter("name");
			String contact = request.getParameter("buying_price");
			
			Supplier supplier = new Supplier();
			supplier.setName(name);
			supplier.setContact(contact);
			
		try {
			int res = SupermartService.insertSupplier(supplier);
			if (res==0) {
				HttpSession session = request.getSession(true);
				session.setAttribute("Error", "supplier Exist");
			} else {
				HttpSession session = request.getSession(true);
				session.setAttribute("Valid", "supplier Entered");
			}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		protected void doUpdatePurchase(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
			int product_id = Integer.parseInt(request.getParameter("product_id"));
			String product_name = request.getParameter("product_name");
			double buying_price = Double.parseDouble(request.getParameter("buying_price"));
			int supplier_id = Integer.parseInt(request.getParameter("supplier_id"));
			int quantity_bought = Integer.parseInt(request.getParameter("quantity_bought"));
			
			Purchase purchase = new Purchase();
			purchase.setProduct_id(product_id);
			purchase.setProduct_name(product_name);
			purchase.setBuying_price(buying_price);
			purchase.setSupplier_id(supplier_id);
			purchase.setQuantity_bought(quantity_bought);
			
		try {
			int res = SupermartService.updatePurchase(purchase);
			if (res==0) {
				HttpSession session = request.getSession(true);
				session.setAttribute("Error", "Unable to update purchase");
			} else {
				HttpSession session = request.getSession(true);
				session.setAttribute("Valid", "Purchase updated");
			}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		protected void doUpdateSales(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
			int order_id = Integer.parseInt(request.getParameter("order_id"));
			int product_id = Integer.parseInt(request.getParameter("product_id"));
			int quantity_sold = Integer.parseInt(request.getParameter("quantity_sold"));
			String customer_name = request.getParameter("customer_name");
			String served_by = request.getParameter("served_by");
			
			Sales sale = new  Sales();
			sale.setOrder_id(order_id);
			sale.setProduct_id(product_id);
			sale.setQuantity_sold(quantity_sold);
			sale.setCustomer_name(customer_name);
			sale.setServed_by(served_by);
			
		try {
			int res = SupermartService.insertSales(sale);
			if (res==0) {
				HttpSession session = request.getSession(true);
				session.setAttribute("Error", "Sale Exist");
			} else {
				HttpSession session = request.getSession(true);
				session.setAttribute("Valid", "Sale Entered");
			}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		protected void doUpdateStock(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
			int stock_id = Integer.parseInt(request.getParameter("stock_id"));
			int product_id = Integer.parseInt(request.getParameter("product_id"));
			int quantity_available = Integer.parseInt(request.getParameter("quantity_available"));
			double selling_price = Double.parseDouble(request.getParameter("selling_price"));
			
			Stock stock =  new Stock();
			stock.setStock_id(stock_id);
			stock.setProduct_id(product_id);
			stock.setQuantity_available(quantity_available);
			stock.setSelling_price(selling_price);
			
		try {
			int res = SupermartService.insertStock(stock);
			if (res==0) {
				HttpSession session = request.getSession(true);
				session.setAttribute("Error", "Stock Exist");
			} else {
				HttpSession session = request.getSession(true);
				session.setAttribute("Valid", "Stock Entered");
			}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		protected void doUpdateSupplier(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
			int supplier_id = Integer.parseInt(request.getParameter("supplier_id"));
			String name = request.getParameter("name");
			String contact = request.getParameter("buying_price");
			
			Supplier supplier = new Supplier();
			supplier.setSupplier_id(supplier_id);
			supplier.setName(name);
			supplier.setContact(contact);
			
		try {
			int res = SupermartService.insertSupplier(supplier);
			if (res==0) {
				HttpSession session = request.getSession(true);
				session.setAttribute("Error", "supplier Exist");
			} else {
				HttpSession session = request.getSession(true);
				session.setAttribute("Valid", "supplier Entered");
			}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		protected void doDeletePurchase(HttpServletRequest request, HttpServletResponse response) {
			int product_id = Integer.parseInt(request.getParameter("product_id"));
			
			try {
				 SupermartService.deletePurchase(product_id);
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		protected void doDeleteSale(HttpServletRequest request, HttpServletResponse response) {
			int order_id = Integer.parseInt(request.getParameter("order_id"));
			
			try {
				 SupermartService.deleteSale(order_id);
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		protected void doDeleteStock(HttpServletRequest request, HttpServletResponse response) {
			int stock_id = Integer.parseInt(request.getParameter("stock_id"));
			
			try {
				 SupermartService.deleteSale(stock_id);
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		protected void doDeleteSupplier(HttpServletRequest request, HttpServletResponse response) {
			int supplier_id = Integer.parseInt(request.getParameter("supplier_id"));
			
			try {
				 SupermartService.deleteSale(supplier_id);
			}catch(Exception e) {
				e.printStackTrace();
			}
		}

}
