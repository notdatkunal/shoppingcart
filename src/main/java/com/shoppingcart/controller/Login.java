package com.shoppingcart.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.shoppingcart.dto.Admin;
import com.shoppingcart.dto.Customer;
import com.shoppingcart.dto.Merchant;

/**
 * Servlet implementation class SaveAdmin
 */
@WebServlet("/validate")

// create save admin page and create account for html
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Login() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("kunal");
		EntityManager em = emf.createEntityManager();
		EntityTransaction et = em.getTransaction();
		response.setContentType("text/html");
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String choice = request.getParameter("choice");
		boolean result = false;
		PrintWriter pw = response.getWriter();
		RequestDispatcher rd = request.getRequestDispatcher("Login.html");
		if(choice==null) {
			pw.print("<script>alert(\"choose an option for login\")</script>");
			rd.include(request, response);
			
			
		}
		if (choice.equals("customer")) {
			Query query = em.createQuery("select a from Customer a where a.email=?1 and a.password=?2");
			query.setParameter(1, email);
			query.setParameter(2, password);
			List<Customer> customer = query.getResultList();
			result = customer.size() >=1;
			if (result) {
				rd= request.getRequestDispatcher("customeroptions.html");
			}else {
				pw.print("<script>alert(\"invalid credentials\")</script>");
			}

		}
		if (choice.equals("admin")) {
			Query query = em.createQuery("select a from Admin a where a.email=?1 and a.password=?2");
			query.setParameter(1, email);
			query.setParameter(2, password);
			List<Admin> admins =(List<Admin>) query.getResultList();
			result = admins.size() >=1;
			
			if (result) {
				Admin admin = admins.get(0);
				HttpSession session   = request.getSession();
				session.setAttribute("Admin", admin);
				rd= request.getRequestDispatcher( "adminoptions.html");
			}else {
				pw.print("<script>alert(\"invalid credentials\")</script>");
			}
			

		}
		if (choice.equals("merchant")) {
			Query query = em.createQuery("select a from Merchant a where a.email=?1 and a.password=?2");
			query.setParameter(1, email);
			query.setParameter(2, password);
			List<Merchant> merchants = query.getResultList();
			result = merchants.size() >=1;
			Merchant merchant =null;

			if (result) {

				merchant= merchants.get(0);
				rd = request.getRequestDispatcher("merchantoptions.html");
			}else {
				pw.print("<script>alert(\"invalid credentials\")</script>");
				}
			if (merchant != null && merchant.getStatus().equals("inactive")) {
				pw.print("<script>alert(\"inactive account\")</script>");
			}
			if (merchant != null && merchant.getStatus().equals("blocked")) {
				pw.print("<script>alert(\"blocked account\")</script>");
			}
			

		}
		
		rd.include(request, response);
	}

}
