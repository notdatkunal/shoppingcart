package com.shoppingcart.controller;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.shoppingcart.dto.Cart;
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
		// TODO Auto-generated method stub
		int productId = Integer.parseInt(request.getParameter("id"));
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("kunal");
		EntityManager em = emf.createEntityManager();
		EntityTransaction et = em.getTransaction();
		Item item = em.find(Item.class, productId);
		Cart cart = item.getCart();
		
		
		request.getRequestDispatcher("customer.jsp").include(request, response);
	}

	
	

}
