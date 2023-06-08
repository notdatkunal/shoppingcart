<%@page import="com.shoppingcart.dto.Cart"%>
<%@page import="org.hibernate.internal.build.AllowSysOut"%>
<%@page import="javax.persistence.EntityTransaction"%>
<%@page import="java.io.PrintWriter"%>
<%@page import="com.shoppingcart.dto.Customer"%>
<%@page import="java.util.List"%>
<%@page import="javax.persistence.Query"%>
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
String id = request.getParameter("id");

if(id!=null){
	int id1 = Integer.parseInt(id);
	Customer customer = em.find(Customer.class,id1);
	Cart cart = customer.getCart();
	
	et.begin();
	em.remove(customer);
	em.remove(cart);
	et.commit();

}


Query query = em.createQuery("select a from Customer a ");
List<Customer> customers =  query.getResultList();


if(customers.size()==0){
	PrintWriter pw = response.getWriter();
	pw.print("<script>alert(\"no customers to be deleted\")</script>");
	request.getRequestDispatcher("admin.html").include(request, response);
	
}else{
%>


<table id="table-1" border="1px" cellspacing="5px">
	<thead>
		<tr>
			<th>customer id</th>
			<th>customer name</th>
			<th>email</th>
			<th>status</th>
		</tr>
	</thead>
	<tbody>
	<% 
for(Customer customer:customers){
	
	
	
	
%>
		<tr>
			<td><%=customer.getId()%></td>
			<td><%=customer.getName()%></td>
			<td><%=customer.getEmail()%></td>
			<td><a href="deletecustomers.jsp?id=<%=customer.getId()%>" title="<%=customer.getStatus()%>">delete permanently</a></td>
			
		</tr>
		<%
		
}} %>
	</tbody>
	
</table>

</body>
</html>