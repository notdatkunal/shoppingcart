<!DOCTYPE html>
<%@page import="com.shoppingcart.dto.Customer"%>
<%@page import="com.shoppingcart.dto.Item"%>
<%@page import="java.util.Base64"%>
<%@page import="com.shoppingcart.dto.Product"%>
<%@page import="java.util.List"%>
<%@page import="javax.persistence.Query"%>
<%@page import="javax.persistence.EntityManager"%>
<%@page import="javax.persistence.Persistence"%>
<%@page import="javax.persistence.EntityManagerFactory"%>
<html>

<head>
	<meta charset="ISO-8859-1">
	<title>Insert title here</title>
</head>

<body><%
EntityManagerFactory emf = Persistence.createEntityManagerFactory("kunal");
EntityManager em = emf.createEntityManager();
Query productquery = em.createQuery("Select p from Product p");
Customer customer = (Customer)session.getAttribute("Customer");
List<Product> products =   productquery.getResultList();
List<Item> items = customer.getCart().getItems();
products = products.subList(0, products.size()>10?10:products.size());

%>
<form action="search.jsp" id="form-1" style="margin-bottom: 10px; display:inline-block;" method="get">
	<input style="width:300px;" name="searchquery" placeholder="Text" type="text"/>
	<input style="" type="submit" value="&#128269;" id="button-2"/>
	<label >Search by :</label>
	<select name="search">
	  	<option value="name" selected>name</option>
  		<option value="category">category</option>
	</select>
	
</form>

	

	<button onclick="">remove items from cart</button>
	<button onclick="showcart.showModal()">show cart</button>


<%if(products.size()==0){ 

	%>
	<h1>no products to be displayed</h1>
	
	<%
}else{

%>
	<table id="products-table"  border="1px" cellspacing="5px">
		<thead>
			<tr>
				<th>Image</th>
				<th>Name</th>
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
				<td><%=product.getPrice() %></td>
				<td><%=product.getStock()!=0?" only \""+product.getStock()+"\" left in stock":"product out of stock" %></td>
				<td><%=product.getMerchant().getName() %></td>
				<td><div style="display:inline-block;  background:lightgrey;"><button onclick="decrement(this)">-</button> <span id="<%=product.getId() %>" style=" width:20px; text-align:center; padding:5px 10px;" class="number" >0</span> <button onclick="increment(this,<%=product.getStock()%>)">+</button></div></td>
				<td><button onclick="add(<%=product.getId()%>)">Add to Cart</button></td>
			</tr>
	<%} %>
		</tbody>
		
	</table>
<%} %>

	<dialog id="showcart">
		<button onclick="showcart.close()">x</button>
		
<%if(items.size()==0){ 

	%>
	<h1 style="color: red;">no products to be displayed</h1>
	
	<%
}else{

%>
	<table id="items-table"  border="1px" cellspacing="5px">
		<thead>
			
		</thead>
		<tbody>
	<%for(Item item:items){ 
		byte[] image = item.getProduct().getImageData();
		String base64ImageData = Base64.getEncoder().encodeToString(image);
	%>
			<tr>
				<td><img style="width: 100px; height: 100px;" src="data:image/png;base64, <%= base64ImageData %>" alt="Image"></td>
				<td><%=item.getName() %></td>
				<td><%=item.getPrice() %></td>
				<td><%=item.getQuantity() %></td>
				<td><button onclick="remove(<%=item.getId()%>)">remove</button></td>
			</tr>
	<%} %>
		</tbody>
		
	</table>
<%} %>

	</dialog>
	
	
	

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