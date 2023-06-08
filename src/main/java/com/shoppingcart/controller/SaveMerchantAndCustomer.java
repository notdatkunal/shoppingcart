package com.shoppingcart.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.shoppingcart.dto.Cart;
import com.shoppingcart.dto.Customer;
import com.shoppingcart.dto.Item;
import com.shoppingcart.dto.Merchant;
import com.shoppingcart.dto.Product;

/**
 * Servlet implementation class SaveMerchantAndCustomer
 */
@WebServlet("/savedata")
public class SaveMerchantAndCustomer extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SaveMerchantAndCustomer() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//here is the code
		String name = request.getParameter("name");
		String email = request.getParameter("email");
		String passworrd = request.getParameter("password");
		String mobilenumber = request.getParameter("number");
		String choice = request.getParameter("choice");
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("kunal");
		EntityManager em = emf.createEntityManager();
		EntityTransaction et = em.getTransaction();
		
		
		if("merchant".equals(choice)) {
			
			Merchant merchant = new Merchant();
			merchant.setName(name);
			merchant.setEmail(email);
			merchant.setPassword(passworrd);
			merchant.setMobilenumber(Long.parseLong(mobilenumber));
			merchant.setStatus("inactive");
			merchant.setProducts(new ArrayList<Product>());
			et.begin();
			em.persist(merchant);
			et.commit();
			request.getRequestDispatcher("Login.html").forward(request, response);
			return;
		}
		
		if("customer".equals(choice)){
			
			Customer customer = new Customer();
			Cart cart = new Cart();
			cart.setTotalPrice(0);
			List<Item> items = new ArrayList<Item>();
			cart.setItems(items);
			cart.setCustomer(customer);
			customer.setCart(cart);
			customer.setName(name);
			customer.setEmail(email);
			customer.setPassword(passworrd);
			customer.setMobilenumber(Long.parseLong(mobilenumber));
			customer.setStatus("active");
			et.begin();
			em.persist(customer);
			em.persist(cart);
			
			et.commit();
			PrintWriter printWriter = response.getWriter();
			printWriter.print("<script> alert(\"Account created Succesufully \") </script>");
			response.setContentType("text/html");
			RequestDispatcher dispatcher = request.getRequestDispatcher("Login.html");
			dispatcher.include(request, response);
			return;
		}
		
		
		
	}

}
