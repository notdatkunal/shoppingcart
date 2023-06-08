package com.shoppingcart.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import com.shoppingcart.dto.Merchant;
import com.shoppingcart.dto.Product;

/**
 * Servlet implementation class AddItem
 */
@MultipartConfig
public class AddItem extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddItem() {
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

	    Part imagePart = request.getPart("image");
	    InputStream imageContent = imagePart.getInputStream();
	    byte[] imageData = convertInputStreamToByteArray(imageContent); 
	    
	    String name = request.getParameter("itemname");
	    String price = request.getParameter("price");
	    String category = request.getParameter("category");
	    String stock = request.getParameter("stock");
	    HttpSession session = request.getSession();
	    Merchant merchant = (Merchant) session.getAttribute("Merchant");
	    
	    EntityManagerFactory emf = Persistence.createEntityManagerFactory("kunal");
	    EntityManager em = emf.createEntityManager();
	    EntityTransaction et = em.getTransaction();
	    
	    Product product = new Product();
	    product.setCategory(category);
	    product.setImageData(imageData);
	    product.setMerchant(merchant);
	    product.setName(name);
	    product.setPrice(Integer.parseInt(price));
	    product.setStock(Integer.parseInt(stock));
	    
	    merchant.getProducts().add(product);
	    
	    et.begin();
	    em.persist(product);
	    em.merge(merchant);
	    et.commit();
	    
		
		request.getRequestDispatcher("merchant.jsp").forward(request, response);
	}
	
	public byte[] convertInputStreamToByteArray(InputStream inputStream) throws IOException {
	    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
	    byte[] buffer = new byte[4096];
	    int bytesRead;
	    while ((bytesRead = inputStream.read(buffer)) != -1) {
	        outputStream.write(buffer, 0, bytesRead);
	    }
	    return outputStream.toByteArray();
	}

}
