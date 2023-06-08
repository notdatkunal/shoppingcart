<%@page import="java.util.Base64"%>
<%@page import="com.shoppingcart.dto.Product"%>
<%@page import="java.util.List"%>
<%@page import="com.shoppingcart.dto.Merchant"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>

<head>
	<meta charset="ISO-8859-1">
	<title>Insert title here</title>
</head>

<body>
<a href="LogOut">LogOut</a>
<%
Merchant merchant =(Merchant)session.getAttribute("Merchant");


%>
<h1><%=merchant.getName() %></h1>
	<button onclick="add.showModal()">add Product</button>
	
	

<%

List<Product> products = merchant.getProducts();
if(products.size()==0){
	
%>
	<h1 style="color: red">no products listed</h1>
<% }else{
	
	
		
	
	
%>
<center><h1>Products</h1></center>
	<table id="table-1" border="1px" cellspacing="5px">
		<thead>
			<tr>
				<th>Image</th>
				<th>Name</th>
				<th>category</th>
				<th>Price</th>
				<th>Stock</th>
				<th>Update</th>
				<th>Remove</th>
			</tr>
		</thead>
		<tbody>
		<%
		for(Product product:products){
			
			byte[] image = product.getImageData();
			String base64ImageData = Base64.getEncoder().encodeToString(image);
			
		%>
			<tr>
				<td><img style="width: 200px; height: 200px;" src="data:image/png;base64, <%= base64ImageData %>" alt="Image"></td>
				<td><%=product.getName() %></td>
				<td><%=product.getCategory() %></td>
				<td><%=product.getPrice() %></td>
				<td><%=product.getStock() %></td>
				<td><button><a href="updateproduct.jsp?id=<%=product.getId()%>">update</a></button></td>
				<td><button><a href="DeleteProduct?id=<%=product.getId()%>" >delete</a></button></td>
			</tr>
			<%} %>
		</tbody>
		
	</table>
<%} %>


	<dialog id="add">
	<button onclick="add.close()">x</button>
		<form action="AddItem" method="POST" enctype="multipart/form-data">
			<label for="image">Enter item image:</label>
			<input type="file" name="image" id="">
			<br>
			<label for="itemname">Enter item name:</label>
			<input id="itemname" name="itemname" placeholder="Text" type="text" />
			<br>
			<label for="category">Enter Category:</label>
			<input id="category" name="category" placeholder="Text" type="text" />
			<br>
			<label for="price">Enter Price:</label>
			<input type="number" name="price"/>
			<br>
			<label for="stock">Enter Stock:</label>
			<input type="number" name="stock"/>
			<br>
			<input type="submit" value="ADD" id="button-1" />
		</form>
	</dialog>
	
	


</body>

</html>