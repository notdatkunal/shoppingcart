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

import com.shoppingcart.dto.Merchant;
import com.shoppingcart.dto.Product;

/**
 * Servlet implementation class DeleteProduct
 */
public class DeleteProduct extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteProduct() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("kunal");
		EntityManager em  = emf.createEntityManager();
		EntityTransaction et = em.getTransaction();
		String ide = request.getParameter("id");
		int id = Integer.parseInt(ide);
		Product product = em.find(Product.class, id);
		
		Merchant merchant = product.getMerchant();
		List<Product> products = merchant.getProducts();
		products.remove(product);
		merchant.setProducts(products);
		
		HttpSession session = request.getSession();
		session.setAttribute("Merchant", merchant);
		et.begin();
		em.merge(merchant);
		em.remove(product);
		et.commit();
		response.sendRedirect("merchant.jsp");
		
	}

}
