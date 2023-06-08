<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="javax.persistence.EntityTransaction"%>
<%@page import="javax.persistence.EntityManager"%>
<%@page import="javax.persistence.Persistence"%>
<%@page import="javax.persistence.EntityManagerFactory"%>
<%@page import="com.shoppingcart.dto.Merchant"%>
<%@page import="com.shoppingcart.dto.Admin"%>
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
Merchant merchant = em.find(Merchant.class, id);
merchant.setStatus("blocked");
Admin admin = (Admin)session.getAttribute("Admin");

merchant.setAdmin(admin);
et.begin();
em.merge(merchant);	
et.commit();
request.getRequestDispatcher("blockmerchants.jsp").forward(request, response);


%>

</body>
</html>