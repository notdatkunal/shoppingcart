package com.shoppingcart.controller;

import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.shoppingcart.dto.Cart;
import com.shoppingcart.dto.Customer;
import com.shoppingcart.dto.Item;

/**
 * Servlet implementation class RemoveItem
 */
public class RemoveItem extends HttpServlet {
       
   
   

	
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//
		int ItemId = Integer.parseInt(request.getParameter("id"));
		HttpSession session = request.getSession();
		Customer customer = (Customer)session.getAttribute("Customer");
		Cart cart= customer.getCart();
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("kunal");
		EntityManager em = emf.createEntityManager();
		EntityTransaction et = em.getTransaction();
		Item item = em.find(Item.class, ItemId);
		et.begin();
		cart.getItems().remove(item);
		em.merge(cart);
		em.merge(customer);
		item.setCart(null);
		item.setProduct(null);
		em.merge(item);
		em.remove(item);
		et.commit();
//		customer.setCart(ItemManager.removeFromCart(cart, productId));
//		
//		ItemManager.removeItem(productId);
		
		session.setAttribute("Customer", customer);
		request.getRequestDispatcher("customer.jsp").include(request, response);
	}

	
	

}
