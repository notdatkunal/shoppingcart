<%@page import="com.shoppingcart.dto.Admin"%>
<%@page import="com.shoppingcart.dto.Customer"%>
<%@page import="javax.persistence.EntityTransaction"%>
<%@page import="javax.persistence.EntityManager"%>
<%@page import="javax.persistence.Persistence"%>
<%@page import="javax.persistence.EntityManagerFactory"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

<%
EntityManagerFactory emf = Persistence.createEntityManagerFactory("kunal");
EntityManager em = emf.createEntityManager();
EntityTransaction et = em.getTransaction();
int id = Integer.parseInt(request.getParameter("id"));
Customer customer = em.find(Customer.class, id);
customer.setStatus("blocked");



et.begin();
em.merge(customer);	
et.commit();
request.getRequestDispatcher("blockcustomers.jsp").forward(request, response);


%>
</body>
</html>