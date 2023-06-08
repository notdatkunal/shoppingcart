package com.shoppingcart.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.shoppingcart.dto.Admin;
import com.shoppingcart.dto.Merchant;

/**
 * Servlet implementation class AddAdmin
 */

public class AddAdmin extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AddAdmin() {
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
		//
		int id = 0;
		String email = null, password = null;
		try {
			id = Integer.parseInt(request.getParameter("id"));
			email = request.getParameter("email");
			password = request.getParameter("password");
			if ("".equals(email)||"".equals(password)) {
				throw new NumberFormatException();
			}
		} catch (NumberFormatException e) {
			PrintWriter printWriter = response.getWriter();
			printWriter.print("<script> alert(\"enter proper info\") </script>");
			response.setContentType("text/html");
			RequestDispatcher dispatcher = request.getRequestDispatcher("createadmin.html");
			dispatcher.include(request, response);
			return;
		}

		Admin admin = new Admin();
		admin.setId(id);
		admin.setEmail(email);
		admin.setPassword(password);
		admin.setMerchants(new ArrayList<Merchant>());
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("kunal");
		EntityManager em = emf.createEntityManager();
		EntityTransaction et = em.getTransaction();
		et.begin();
		em.persist(admin);
		et.commit();
		PrintWriter printWriter = response.getWriter();
		printWriter.print("<script> alert(\"Account created Succesufully \") </script>");
		response.setContentType("text/html");
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("Login.html");
		dispatcher.include(request, response);
	}

}
