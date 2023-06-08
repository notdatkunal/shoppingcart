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
import javax.servlet.http.HttpSession;

import com.shoppingcart.dto.Customer;
import com.shoppingcart.dto.Item;
import com.shoppingcart.dto.Product;

/**
 * Servlet implementation class AddToCart
 */
public class AddToCart extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddToCart() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		int id = Integer.parseInt(request.getParameter("id"));
		int quantity = Integer.parseInt(request.getParameter("qty"));
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("kunal");
		HttpSession session = request.getSession();
		EntityManager em = emf.createEntityManager();
		EntityTransaction et = em.getTransaction();
		try {
			Item item =  ItemManager.getItem(em.find(Product.class, id), quantity);
			Customer customer = (Customer)session.getAttribute("Customer");
			System.out.println(customer);
			customer.getCart().getItems().add(item);
			item.setCart(customer.getCart());
			et.begin();
			em.persist(item);
			em.merge(customer.getCart());
			em.merge(customer);
			et.commit();
		} catch (ItemException e) {
			//out of stock
			response.getWriter().print("<script>alert(\"not enough quantity\")</script>");
		} catch (ItemAlreadyExistsException e) {
			// TODO Auto-generated catch block
			response.getWriter().print("<script>alert(\"Item already in cart\")</script>");
			
		}
		
		request.getRequestDispatcher("customer.jsp").include(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
