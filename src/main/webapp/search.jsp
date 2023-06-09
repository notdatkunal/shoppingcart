<%@page import="java.util.Base64"%>
<%@page import="com.shoppingcart.dto.Product"%>
<%@page import="java.util.List"%>
<%@page import="javax.persistence.EntityManager"%>
<%@page import="javax.persistence.Persistence"%>
<%@page import="javax.persistence.EntityManagerFactory"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<% 
String searchquery = request.getParameter("searchquery");
String searchtype = request.getParameter("search");
EntityManagerFactory emf = Persistence.createEntityManagerFactory("kunal");
EntityManager em = emf.createEntityManager();
String search = "";
if("name".equals(searchtype))
	search = "SELECT a from Product a WHERE a.name LIKE :searchQuery";
else
	search = "SELECT a from Product a WHERE a.category LIKE :searchQuery";

List<Product> products = em.createQuery(search)
	                           .setParameter("searchQuery", "%"+searchquery + "%")
	                           .getResultList();
%>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<h3>search results for : <%=searchquery%> | searching by : <%=searchtype %></h3>

<table id="products-table"  border="1px" cellspacing="5px">
		<thead>
			<tr>
				<th>Image</th>
				<th>Name</th>
				<th>Category</th>
				<th>Price</th>
				<th>Stock</th>
				<th>Merchant Name</th>
				<th>Quantity</th>
				<th>add to cart</th>
			</tr>
		</thead>
		<tbody>
	<%for(Product product:products){ 
		byte[] image = product.getImageData();
		String base64ImageData = Base64.getEncoder().encodeToString(image);
	%>
			<tr>
				<td><img style="width: 200px; height: 200px;" src="data:image/png;base64, <%= base64ImageData %>" alt="Image"></td>
				<td><%=product.getName() %></td>
				<td><%=product.getCategory() %></td>
				<td><%=product.getPrice() %></td>
				<td><%=product.getStock()!=0?" only \""+product.getStock()+"\" left in stock":"product out of stock" %></td>
				<td><%=product.getMerchant().getName() %></td>
				<td><div style="display:inline-block;  background:lightgrey;"><button onclick="decrement(this)">-</button> <span id="<%=product.getId() %>" style=" width:20px; text-align:center; padding:5px 10px;" class="number" >0</span> <button onclick="increment(this,<%=product.getStock()%>)">+</button></div></td>
				<td><button onclick="add(<%=product.getId()%>)">Add to Cart</button></td>
			</tr>
	<%} %>
		</tbody>
		
	</table>
</body>
<script>
	function increment(element,stock) {
    var numberElement = element.parentNode.querySelector(".number");
    var value = parseInt(numberElement.textContent);
    if(value<stock){
    value++;
    numberElement.textContent = value;}
  }

  function decrement(element) {
    var numberElement = element.parentNode.querySelector(".number");
    var value = parseInt(numberElement.textContent);
    if(value>0){
    value--;
    numberElement.textContent = value;}
  }
  function add(id){
		var qtyE = document.getElementById(id);
		var qty = parseInt(qtyE.innerText);
		if(qty==0){
			alert("no stocks to be added");
			return;
			}
		var url = 'AddToCart?id='+id+'&qty='+qty;
		window.location.href = url; 

	  }

	function remove(id){

		document.location.href = "RemoveItem?id="+id;

		}

</script>
</html>