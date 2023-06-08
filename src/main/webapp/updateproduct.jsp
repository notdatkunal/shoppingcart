<%@page import="java.util.Base64"%>
<%@page import="com.shoppingcart.dto.Product"%>
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
<%
String args = request.getParameter("id");
int id = Integer.parseInt(args);
EntityManagerFactory emf = Persistence.createEntityManagerFactory("kunal");
EntityManager em = emf.createEntityManager();
Product product = em.find(Product.class, id);
byte[] image = product.getImageData();
String base64ImageData = Base64.getEncoder().encodeToString(image);


%>
</head>
<body>
<h1>update product</h1>


<img style="width: 300px; height: 300px;" src="data:image/png;base64, <%= base64ImageData %>" alt="Image">

	<form action="UpdateProduct?id=<%=id%>" method="POST">
	
		<label id="title">choose fields you want to change</label>
		
		<br>
		
		<input type="radio" name="field" value="name" /> 
		<label>name</label>
		
		<input type="radio" name="field" value="category" />
		<label>category</label>
	
		<input type="radio" name="field" value="price" /> 
		<label>price</label>
	
		<input type="radio" name="field" value="stock" /> 
		<label>stock</label>
		
		<br>
		
		<label for="value">update data</label> <br>
		<input type="text"  name="value"/>
		<br>
		
		<input type="submit" value="Submit" />
		
	</form>
</body>
</html>