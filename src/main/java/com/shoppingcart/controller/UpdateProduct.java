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

import com.shoppingcart.dto.Merchant;
import com.shoppingcart.dto.Product;

/**
 * Servlet implementation class UpdateProduct
 */
public class UpdateProduct extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateProduct() {
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
		// TODO Auto-generated method stub
		
		String string = request.getParameter("id");
		int id = Integer.parseInt(string);
		String choice = request.getParameter("field");
		String value = request.getParameter("value");
		if(choice==null||value.isEmpty()) {
			response.getWriter().write("<script>alert(\"please provide proper input\");</script>");
			request.getRequestDispatcher("updateproduct.jsp").include(request, response);
			return;
		}
		
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("kunal");
		EntityManager em  = emf.createEntityManager();
		EntityTransaction et = em.getTransaction();
		Product product = em.find(Product.class, id);
		if("name".equals(choice)) {
			product.setName(value);
		}
		if("category".equals(choice)) {
			product.setCategory(value);
		}
		if("price".equals(choice)) {
			product.setPrice(Double.parseDouble(value));
		}
		if("stock".equals(choice)) {
			product.setStock(Integer.parseInt(value));
		}
		Merchant merchant = product.getMerchant();
		HttpSession session = request.getSession();
		session.setAttribute("Merchant", merchant);
		et.begin();
		em.merge(product);
		em.merge(merchant);
		et.commit();
		response.sendRedirect("merchant.jsp");
		
	}

}
